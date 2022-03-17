package foam

import foam.BaseMod.DefaultRegistries
import foam.data.declistops.AllIn
import foam.data.reglistops.In
import foam.data.{#:, DecList, RNil, RegList}
import foam.registry.dsl.{ForgeDecMod, ModDecMod}
import foam.registry.{WrappedRegistry, RegDec}
import foam.syntax.all._
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.{ForgeRegistries, IForgeRegistryEntry}

/** Entrypoint for any Foam mod. Example: [[Foam]] itself. */
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
	protected def modules: Seq[Module[_]]

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
					case m: ModDecMod[A] => m.register(registry(dec).get, EventBus, mod) //"mod" here isn't a DecMod but a CbMod. sorry.
					case m: ForgeDecMod[A] => m.register(registry(dec).get, mod)
				}
			})
		}

		declarations.foreach(registries, consumer)
	}

	/** returns the registered object for any [[RegDec]][A] as long as there is an available registry for that type */
	def get[A <: IForgeRegistryEntry[A]](declaration: RegDec[A])(implicit ev: A In Registries): A = ev(registries).get(declaration).get

	/** returns the registered object for any [[RegDec]][A] as long as there is an available registry for that type */
	def apply[A <: IForgeRegistryEntry[A]](declaration: RegDec[A])(implicit ev: A In Registries): A = get(declaration)

	// MODULES ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	/** Provides declarations and the ability to separate mod data in a modular way.
	 *
	 *  The implicit [[AllIn]] constraint ensures that all types declared in a given module have an available registry.
	 */
	abstract class Module[D <: DecList](implicit ev: D AllIn Registries) {
		protected def declarations: D

		/** used to preserve type information in the [[register]] call, even though modules are provided to the mod as Module[_] */
		private[BaseMod] def init(): Unit = register[D](declarations)
	}

	modules.foreach(_.init()) // what actually initializes all mod data

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
}

object BaseMod {
	/** The list of default registry types */
	type DefaultRegistries = Item #: Block #: Fluid #: Enchantment #: Potion #: RNil
}