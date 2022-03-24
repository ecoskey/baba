package net.emersoncoskey.baba

import net.emersoncoskey.baba.data.declistops.AllIn
import net.emersoncoskey.baba.data.reglistops.In
import net.emersoncoskey.baba.data.{DecList, RNil, RegList}
import net.emersoncoskey.baba.registry.dsl.{ForgeDecMod, ModDecMod}
import net.emersoncoskey.baba.registry.{RegDec, WrappedRegistry}
import net.emersoncoskey.baba.syntax.all._
import net.emersoncoskey.baba.util.{DefaultRegistries, PaintingType}
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.{ForgeRegistries, IForgeRegistryEntry}

/** Entrypoint for any Baba mod. Example: [[Baba]] itself. */
trait BaseMod {
	/**
	 * note: used because a constant value is necessary when using it as the Mod() annotation parameter.
	 *
	 * make sure to NOT add a type annotation, because otherwise it is not considered a "constant value" and will fail
	 */
	val _modId: String

	/** returns the id string for this mod. */
	final lazy val ModId: String = _modId

	/** should return the list of modules to be loaded upon startup */
	protected def modules: Seq[Module]

	/** the forge event bus for this mod's lifecycle events */
	val EventBus: IEventBus = FMLJavaModLoadingContext.get.getModEventBus

	// REGISTRY STUFF ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	/** List of available registry *types* for this mod */
	type Registries <: RegList

	/** List of available registries for this mod. */
	protected val registries: Registries

	/** registers all elements of a [[RegList]] to the existing list of registries */
	private def register[D <: DecList](declarations: D)(implicit ev: D AllIn Registries): Unit = {
		val mod = this //bring outer class instance into scope

		val consumer: AllIn.PolyConsumer = new AllIn.PolyConsumer {
			def apply[A <: IForgeRegistryEntry[A]](decs: Seq[RegDec[A]], registry: WrappedRegistry[A]): Unit = decs.foreach(dec => {
				registry.register(dec)

				dec.modifiers.foreach {
					//case m: ModDecMod[A] => m.register(registry(dec).get, EventBus, mod) //"mod" here isn't a DecMod but a CbMod. sorry.
					case m: ModDecMod[A] => EventBus.addListener(m.priority, m.receiveCanceled, m.eventClass, m.handleEvent(registry(dec).get, _, mod))
					case m: ForgeDecMod[A] => {
						val ForgeBus = MinecraftForge.EVENT_BUS
						ForgeBus.addListener(m.priority, m.receiveCanceled, m.eventClass, m.handleEvent(registry(dec).get, _, mod))
					}
				}
			})
		}

		declarations.foreach(registries, consumer)
	}

	/** returns the registered object for any [[RegDec]][A] as long as there is an available registry for that type */
	def get[A <: IForgeRegistryEntry[A]](declaration: RegDec[A])(implicit ev: A In Registries): A = ev(registries).get(declaration).get

	/** returns the registered object for any [[RegDec]][A] as long as there is an available registry for that type */
	def apply[A <: IForgeRegistryEntry[A]](declaration: RegDec[A])(implicit ev: A In Registries): A = get(declaration)

	/** returns all objects of a given type in the registries */
	def getAll[A <: IForgeRegistryEntry[A]](implicit ev: A In Registries): List[A] = ev(registries).getAll

	// MODULES ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	/** Provides declarations and the ability to separate mod data in a modular way.
	 *
	 *  The implicit [[AllIn]] constraint ensures that all types declared in a given module have an available registry.
	 */
	trait Module {
		type D <: DecList
		val decs: D
		private [BaseMod] val allIn: D AllIn Registries //jank but it needs to survive a path dependent type somehow
	}

	object Module {
		def apply[d <: DecList](declarations: d)(implicit ev: d AllIn Registries): Module = new Module {
			type D = d
			val decs: D = declarations
			val allIn: D AllIn Registries = ev
		}
	}

	modules.foreach(m => register[m.D](m.decs)(m.allIn))

	// UTIL METHODS ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	/** Returns a [[ResourceLocation]] of the form ModId:path */
	def modLoc(path: String): ResourceLocation = new ResourceLocation(ModId, path)

	/** Returns a [[ResourceLocation]] of the form minecraft:path */
	def mcLoc(path: String): ResourceLocation = new ResourceLocation(path)

	/** Returns a [[RegList]] of all default registry types. The usual way to override [[registries]] */
	protected def defaultRegistries: DefaultRegistries =
		getItemReg        #:
		getBlockReg       #:
		getFluidReg       #:
		getEnchantmentReg #:
		getPotionReg      #:
		getPaintingReg    #:
		RNil

	/** Returns a default [[WrappedRegistry]] for [[Item]]s */
	protected def getItemReg: WrappedRegistry[Item] = new WrappedRegistry[Item](this, ForgeRegistries.ITEMS)

	/** Returns a default [[WrappedRegistry]] for [[Block]]s */
	protected def getBlockReg: WrappedRegistry[Block] = new WrappedRegistry[Block](this, ForgeRegistries.BLOCKS)

	/** Returns a default [[WrappedRegistry]] for [[Fluid]]s */
	protected def getFluidReg: WrappedRegistry[Fluid] = new WrappedRegistry[Fluid](this, ForgeRegistries.FLUIDS)

	/** Returns a default [[WrappedRegistry]] for [[Enchantment]]s */
	protected def getEnchantmentReg: WrappedRegistry[Enchantment] = new WrappedRegistry[Enchantment](this, ForgeRegistries.ENCHANTMENTS)

	/** Returns a default [[WrappedRegistry]] for [[Potion]]s */
	protected def getPotionReg: WrappedRegistry[Potion] = new WrappedRegistry[Potion](this, ForgeRegistries.POTIONS)

	/** Returns a default [[WrappedRegistry]] for [[PaintingType]]s */
	protected def getPaintingReg: WrappedRegistry[PaintingType] = new WrappedRegistry[PaintingType](this, ForgeRegistries.PAINTING_TYPES)
}

object BaseMod {
	trait Default extends BaseMod {
		final type Registries = DefaultRegistries
		final val registries: DefaultRegistries = defaultRegistries
	}
}