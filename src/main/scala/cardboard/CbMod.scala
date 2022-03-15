package cardboard

import cardboard.CbMod.DefaultRegistries
import cardboard.data.declistops.AllIn
import cardboard.data.reglistops.In
import cardboard.data.{#:, DecList, RNil, RegList}
import cardboard.registry.dsl.{ForgeDecMod, ModDecMod}
import cardboard.registry.{CbRegistry, RegDec}
import cardboard.syntax.all._
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.{ForgeRegistries, IForgeRegistryEntry}

trait CbMod {
	/**
	 * note: used because a constant value is necessary when using it as the Mod() annotation parameter.
	 *
	 * make sure to NOT add a type annotation, because otherwise it is not considered a "constant value" and will fail
	 */
	val _modId: String

	final lazy val ModId: String = _modId

	def modules: Seq[Module[_]]

	val EventBus: IEventBus = FMLJavaModLoadingContext.get.getModEventBus

	// REGISTRY STUFF ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	type Registries <: RegList
	protected val registries: Registries

	private def register[D <: DecList](declarations: D)(implicit ev: D AllIn Registries): Unit = {
		val mod = this //bring outer class instance into scope

		val consumer: AllIn.PolyConsumer = new AllIn.PolyConsumer {
			def apply[A <: IForgeRegistryEntry[A]](decs: Seq[RegDec[A]], registry: CbRegistry[A]): Unit = decs.foreach(dec => {
				registry.register(dec)

				dec.mods.foreach {
					case m: ModDecMod[A] => m.register(registry(dec).get, EventBus, mod) //"mod" here isn't a DecMod but a CbMod. sorry.
					case m: ForgeDecMod[A] => m.register(registry(dec).get, mod)
				}
			})
		}

		declarations.foreach(registries, consumer)
	}

	// get registry for type A, get RegistryObject[A] associated with dec, get registered A from the RegistryObject
	def get[A <: IForgeRegistryEntry[A]](dec: RegDec[A])(implicit ev: A In Registries): A = ev(registries).get(dec).get

	def apply[A <: IForgeRegistryEntry[A]](dec: RegDec[A])(implicit ev: A In Registries): A = get(dec)

	// MODULES ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	abstract class Module[D <: DecList](implicit ev: D AllIn Registries) {
		protected def declarations: D

		private[CbMod] def init(): Unit = register[D](declarations)
	}

	modules.foreach(_.init()) // what actually initializes all mod data

	// UTIL METHODS ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	def modLoc(path: String): ResourceLocation = new ResourceLocation(ModId, path)

	def mcLoc(path: String): ResourceLocation = new ResourceLocation(path)

	protected def defaultRegistries: DefaultRegistries =
		getItemReg        #:
		getBlockReg       #:
		getFluidReg       #:
		getEnchantmentReg #:
		getPotionReg      #:
		RNil

	protected def getItemReg = new CbRegistry[Item](this, ForgeRegistries.ITEMS)
	protected def getBlockReg = new CbRegistry[Block](this, ForgeRegistries.BLOCKS)
	protected def getFluidReg = new CbRegistry[Fluid](this, ForgeRegistries.FLUIDS)
	protected def getEnchantmentReg = new CbRegistry[Enchantment](this, ForgeRegistries.ENCHANTMENTS)
	protected def getPotionReg = new CbRegistry[Potion](this, ForgeRegistries.POTIONS)
}

object CbMod {
	type DefaultRegistries =
		Item #: Block #: Fluid #: Enchantment #: Potion #: RNil
}