package net.emersoncoskey.baba

import cats.syntax.functor._
import cats.syntax.traverse._
import cats.{Id, ~>}
import net.emersoncoskey.baba.registry.{Declare, EventHandler, HandleEvent, Register, RegisterA}
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.{EventPriority, IEventBus}
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.{IForgeRegistry, IForgeRegistryEntry, RegistryObject}

object Baba {
	def apply(modId: String, actions: Register[_]*): Baba =
		new Baba(modId, FMLJavaModLoadingContext.get.getModEventBus, actions.map(_.void).sequence.void)
}

class Baba private(modId: String, modBus: IEventBus, actions: Register[Unit]) {
	private val registryObjects: Map[ResourceLocation, RegistryObject[_]] = run(actions)

	private def run[A](prog: Register[Unit]): Map[ResourceLocation, RegistryObject[_]] = {
		var tempList: List[(ResourceLocation, RegistryObject[_])] = Nil //eww a var

		@inline def addHandler(bus: IEventBus, h: EventHandler.Normal): Unit =
			bus.addListener[h.E](h.priority, h.receiveCancelled, h.filter, (e: h.E) => h.handler(e))

		@inline def addGenericHandler(bus: IEventBus, h: EventHandler.Generic): Unit =
			bus.addGenericListener[h.E[h.A], h.A](h.paramFilter, h.priority, h.receiveCancelled, h.eventFilter, (e: h.E[h.A]) => h.handler(e))

		val interpreter = new (RegisterA ~> Id) {
			def apply[X](fa: RegisterA[X]): Id[X] = fa match {
				case dec: Declare[r, a] => {
					val resourceLoc = new ResourceLocation(modId, dec.name)
					val regInfo = dec.ev

					def handler(e: RegistryEvent.Register[r], modId: String): Unit  = {
						val registry: IForgeRegistry[r] = e.getRegistry
						val obj: r = dec.getObj().setRegistryName(new ResourceLocation(modId, dec.name))
						registry.register(obj)
					}

					modBus.addGenericListener[RegistryEvent.Register[r], r](
						regInfo.objClass,
						EventPriority.NORMAL,
						false,
						classOf[RegistryEvent.Register[r]],
						event => handler(event, modId)
					)

					val regObj: RegistryObject[a] = RegistryObject.of[r, a](resourceLoc, regInfo.key, modId)
					tempList +:= (resourceLoc, regObj) //eww mutation
					regObj
				}

				case HandleEvent(handler: EventHandler.Mod)          => addHandler(modBus, handler)
				case HandleEvent(handler: EventHandler.Forge)        => addHandler(MinecraftForge.EVENT_BUS, handler)
				case HandleEvent(handler: EventHandler.GenericMod)   => addGenericHandler(modBus, handler)
				case HandleEvent(handler: EventHandler.GenericForge) => addGenericHandler(MinecraftForge.EVENT_BUS, handler)
			}
		}

		prog.foldMap(interpreter)
		Map.from(tempList)
	}

	private val getter: RegisterA ~> Id = new (RegisterA ~> Id) {
		def apply[A](fa: RegisterA[A]): Id[A] = fa match {
			case dec: Declare[r, a] => registryObjects(new ResourceLocation(modId, dec.name)).asInstanceOf[RegistryObject[a]]
			case h: HandleEvent => ()
		}
	}

	def apply[A](action: Register[A]): A = action.foldMap(getter)
	def apply[A <: IForgeRegistryEntry[A]](loc: ResourceLocation): RegistryObject[A] = registryObjects(loc).asInstanceOf[RegistryObject[A]]
	def apply[A <: IForgeRegistryEntry[A]](loc: String): RegistryObject[A] = registryObjects(new ResourceLocation(modId, loc)).asInstanceOf[RegistryObject[A]]
}


