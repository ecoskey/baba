package net.emersoncoskey.cardboard

import net.emersoncoskey.cardboard.registry.Reg.Ops
import net.emersoncoskey.cardboard.registry.block.CbBlock
import net.emersoncoskey.cardboard.registry.dsl.{DecMod, ForgeDecMod, ModDecMod}
import net.emersoncoskey.cardboard.registry.item.CbItem
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.{Item, Items}
import net.minecraft.world.level.block.Block
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.TickEvent
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent
import net.minecraftforge.registries.{DeferredRegister, ForgeRegistries, RegistryObject}
import org.apache.logging.log4j.Logger

/*object CbMod {
	trait EventListener {
		type E <: Event
		final def register(eventBus: IEventBus, mod: CbMod): Unit = eventBus.addListener[E](e => handleEvent(e, mod))
		def handleEvent(event: E, mod: CbMod): Unit
	}

	object EventListener {
		def apply[e <: Event](f: (e, CbMod) => Unit): EventListener = new EventListener {
			type E = e
			override def handleEvent(event: E, mod: CbMod): Unit = f(event, mod)
		}
	}
}*/

trait CbMod {
	/** note: used because a constant value is necessary when using it as the Mod() annotation parameter */
	protected val _modId: String

	final lazy val ModId: String = _modId

	val EventBus: IEventBus
	val Logger  : Logger
	val Modules : Seq[CbModule]

	/* [REGISTRY STUFF] ***************************************************************************************************************************************/

	private val itemReg : DeferredRegister[Item]  = DeferredRegister.create(ForgeRegistries.ITEMS, ModId)
	private val blockReg: DeferredRegister[Block] = DeferredRegister.create(ForgeRegistries.BLOCKS, ModId)

	itemReg.register(EventBus)
	blockReg.register(EventBus)

	private val items: Map[CbItem[Item], RegistryObject[Item]] = Map(
		(for {
			m <- Modules
			i <- m.items
			reg = i.reg
		} yield i -> itemReg.register(reg.name, reg.sup)): _*
	)

	private val blocks: Map[CbBlock[Block], RegistryObject[Block]] = Map(
		(for {
			m <- Modules
			b <- m.blocks
			reg = b.reg
		} yield b -> blockReg.register(reg.name, reg.sup)): _*
	)

	final def apply(i: CbItem[Item]): RegistryObject[Item] =
		items.getOrElse(i, throw new IllegalArgumentException(s"CbItem with name ${ i.name } has not been registered"))

	final def apply(b: CbBlock[Block]): RegistryObject[Block] =
		blocks.getOrElse(b, throw new IllegalArgumentException(s"CbBlock with name ${ b.name } has not been registered"))

	/* [EVENT BUS THINGS] *************************************************************************************************************************************/

	val itemMods: List[(RegistryObject[Item], List[DecMod[Item]])] = items.toList.map { case (cbItem, reg) => (reg, cbItem.reg.mods.toList) }
	val blockMods: List[(RegistryObject[Block], List[DecMod[Block]])] = blocks.toList.map { case (cbBlock, reg) => (reg, cbBlock.reg.mods.toList) }

	itemMods.foreach { case (item, mods) => mods.foreach {
		case m: ForgeDecMod[Item] => m.busRegister(item.get, this)
		case m: ModDecMod[Item] => m.busRegister(item.get, EventBus, this)
	}}

	blockMods.foreach { case (block, mods) => mods.foreach {
		case m: ForgeDecMod[Block] => m.busRegister(block.get, this)
		case m: ModDecMod[Block] => m.busRegister(block.get, EventBus, this)
	}}

	/* [UTIL METHODS] *****************************************************************************************************************************************/

	def modLoc(path: String): ResourceLocation = new ResourceLocation(ModId, path)

	def mcLoc(path: String): ResourceLocation = new ResourceLocation(path)
}



