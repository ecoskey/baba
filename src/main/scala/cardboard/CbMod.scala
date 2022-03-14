package cardboard

import cardboard.CbMod.DefaultRegistries
import cardboard.data.declistops.BasisConstraint
import cardboard.data.declistops.BasisConstraint.Basis
import cardboard.data.reglistops.in
import cardboard.registry.{CbRegistry, RegDec}
import cardboard.data.{#:, DecList, RegList, RegNil}
import cardboard.registry.dsl.{ForgeDecMod, ModDecMod}
import cardboard.syntax.all._
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.block.Block
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.{ForgeRegistries, IForgeRegistryEntry}

import scala.annotation.implicitNotFound

trait CbMod {
	/** note: used because a constant value is necessary when using it as the Mod() annotation parameter */
	val _modId: String

	final lazy val ModId: String = _modId

	def modules: Seq[Module[_]]

	val EventBus: IEventBus = FMLJavaModLoadingContext.get.getModEventBus

	//protected def modules : Seq[CbModule]

	// REGISTRY STUFF ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	type Registries <: RegList
	protected val registries: Registries

	private def register[D <: DecList: Basis[Registries]#T](declarations: D): Unit = {
		println("\nregistering module!!!!!!!!!!!!!")
		val mod = this
		val consumer: BasisConstraint.PolyConsumer = new BasisConstraint.PolyConsumer {
			override def apply[A <: IForgeRegistryEntry[A]](decs: Seq[RegDec[A]], registry: CbRegistry[A]): Unit = {
				decs.foreach(dec => {
					registry.register(dec)

					val mods = dec.mods
					mods.foreach {
						case m: ModDecMod[A] => m.register(registry(dec).get, EventBus, mod)
						case m: ForgeDecMod[A] => m.register(registry(dec).get, mod)
					}
				})
			}
		}
		declarations.foreach(registries, consumer)
	}

	def get[A <: IForgeRegistryEntry[A]](dec: RegDec[A])(
		implicit @implicitNotFound("No registry for ${A}s available in this mod") in: A in Registries
	): A = in(registries).get(dec).get

	def apply[A <: IForgeRegistryEntry[A]](dec: RegDec[A])(
		implicit @implicitNotFound("No registry for ${A}s available in this mod") in: A in Registries
	): A = get(dec)

	// MODULES ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	abstract class Module[D <: DecList: Basis[Registries]#T] {
		protected def declarations: D

		private [CbMod] def init(): Unit = register[D](declarations)
	}

	modules.foreach(_.init())

	// UTIL METHODS ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	def modLoc(path: String): ResourceLocation = new ResourceLocation(ModId, path)

	def mcLoc(path: String): ResourceLocation = new ResourceLocation(path)

	protected def defaultRegistries: DefaultRegistries = getItemReg #: getBlockReg #: getEnchantmentReg #: getPotionReg #: RegNil

	protected def getItemReg = new CbRegistry[Item](this, ForgeRegistries.ITEMS)
	protected def getBlockReg = new CbRegistry[Block](this, ForgeRegistries.BLOCKS)
	protected def getEnchantmentReg = new CbRegistry[Enchantment](this, ForgeRegistries.ENCHANTMENTS)
	protected def getPotionReg = new CbRegistry[Potion](this, ForgeRegistries.POTIONS)
}

object CbMod {
	type DefaultRegistries = Item #: Block #: Enchantment #: Potion #: RegNil
}