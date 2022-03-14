package cardboard

import cardboard.CbMod.DefaultRegistries
import cardboard.data.hlistops.{get, in}
import cardboard.registry.{CbRegistry, RegDec}
import cardboard.data.{::, HList, HNil}
import cardboard.registry.dsl.{ForgeDecMod, ModDecMod}
import cardboard.syntax.hlist._
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.block.Block
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.ForgeRegistries

import scala.annotation.implicitNotFound

abstract class CbMod {
	/** note: used because a constant value is necessary when using it as the Mod() annotation parameter */
	protected val modId: String

	final lazy val ModId: String = modId

	val EventBus: IEventBus = FMLJavaModLoadingContext.get.getModEventBus

	protected def modules : Seq[CbModule]

	// REGISTRY STUFF ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	type Registries <: HList
	protected val registries: Registries

	protected def defaultRegistries: DefaultRegistries = getItemReg :: getBlockReg :: getEnchantmentReg :: getPotionReg :: HNil

	protected def getItemReg = new CbRegistry[Item](this, ForgeRegistries.ITEMS)
	protected def getBlockReg = new CbRegistry[Block](this, ForgeRegistries.BLOCKS)
	protected def getEnchantmentReg = new CbRegistry[Enchantment](this, ForgeRegistries.ENCHANTMENTS)
	protected def getPotionReg = new CbRegistry[Potion](this, ForgeRegistries.POTIONS)

	def get(dec: RegDec)(implicit @implicitNotFound("No registry for ${A}s available in this mod") in: dec.A in Registries): dec.A = in(registries).get(dec).get

	def apply(dec: RegDec)(implicit @implicitNotFound("No registry for ${A}s available in this mod") in: dec.A in Registries): dec.A = get(dec)

	// register all declarations

	private def register(dec: RegDec): Unit = registries.getOption[dec.A].foreach(registry => {
		registry.register(dec)

		val mods = dec.mods
		mods.foreach {
			case m: ModDecMod[dec.A] => m.register(registry(dec).get, EventBus, this)
			case m: ForgeDecMod[dec.A] => m.register(registry(dec).get, this)
		}
	})

	modules.flatMap(_.decs).foreach(register)

	// UTIL METHODS ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	def modLoc(path: String): ResourceLocation = new ResourceLocation(ModId, path)

	def mcLoc(path: String): ResourceLocation = new ResourceLocation(path)
}

object CbMod {
	type DefaultRegistries = Item :: Block :: Enchantment :: Potion :: HNil
}