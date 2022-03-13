package cardboard

import cardboard.CbMod.DefaultRegistries
import cardboard.registry.{CbRegistry, RegDec}
import cardboard.shapelesscompat.hlist.contraints.FConstraint
import cardboard.shapelesscompat.hlist.ops.FBoundMapped
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.block.Block
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import net.minecraftforge.registries.{ForgeRegistries, IForgeRegistryEntry}
import shapeless.ops.hlist.Selector
import shapeless.{::, HList, HNil, IsDistinctConstraint}

import scala.annotation.implicitNotFound

abstract class CbMod[R <: HList: IsDistinctConstraint](
	implicit
	registryEntryConstraint: FConstraint[R, IForgeRegistryEntry],
	toRegistryMap: FBoundMapped[R, CbRegistry, IForgeRegistryEntry],
) {
	/** note: used because a constant value is necessary when using it as the Mod() annotation parameter */
	protected val modId: String

	final lazy val ModId: String = modId

	val EventBus: IEventBus = FMLJavaModLoadingContext.get.getModEventBus

	protected def modules : Seq[CbModule[R]]
	private val _modules: Seq[CbModule[R]] = modules

	// REGISTRY STUFF ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	type RegistryTypes = R
	type Registries = toRegistryMap.Out

	protected val registries: Registries

	protected def defaultRegistries: DefaultRegistries = getItemReg :: getBlockReg :: getEnchantmentReg :: getPotionReg :: HNil

	protected def getItemReg = new CbRegistry[Item](this, ForgeRegistries.ITEMS)
	protected def getBlockReg = new CbRegistry[Block](this, ForgeRegistries.BLOCKS)
	protected def getEnchantmentReg = new CbRegistry[Enchantment](this, ForgeRegistries.ENCHANTMENTS)
	protected def getPotionReg = new CbRegistry[Potion](this, ForgeRegistries.POTIONS)

	def get[A <: IForgeRegistryEntry[A]](dec: RegDec[A])(
		implicit
		@implicitNotFound("No registry for ${A}s available in this mod")
		ev: Selector[Registries, CbRegistry[A]]
	): A = ev(registries).get(dec).get

	def apply[A <: IForgeRegistryEntry[A]](dec: RegDec[A])(
		implicit
		@implicitNotFound("No registry for ${A}s available in this mod")
		ev: Selector[Registries, CbRegistry[A]]
	): A = get(dec)

	// register all declarations

	/*private def registerDec(reg: Reg): Unit = {
		def withRegistry[A <: IForgeRegistryEntry[A]](registry: CbRegistry[A], reg: Reg.Aux[A]): Unit = {
			val registryDec = reg.reg
			registry.register(registryDec)

			val mods = registryDec.mods
			mods.foreach {
				case m: ModDecMod[reg.A] => m.register(registry(registryDec).get, eventBus, this)
				case m: ForgeDecMod[reg.A] => m.register(registry(registryDec).get, this)
			}
		}

		reg match {
			case r: Reg.Aux[Item] => withRegistry[Item](totalRegistries.get[Item], r)
			case r: Reg.Aux[Block] => withRegistry[Block](totalRegistries.get[Block], r)
			case r: Reg.Aux[Enchantment] => withRegistry[Enchantment](totalRegistries.get[Enchantment], r)
			case r: Reg.Aux[Potion] => withRegistry[Potion](totalRegistries.get[Potion], r)
		}
	}*/

	//modules.flatMap(_.registryDecs).foreach(registerDec)

	// UTIL METHODS ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	def modLoc(path: String): ResourceLocation = new ResourceLocation(ModId, path)

	def mcLoc(path: String): ResourceLocation = new ResourceLocation(path)
}

object CbMod {
	type DefaultRegistryTypes = Item :: Block :: Enchantment :: Potion :: HNil

	implicit val defaultRegistriesMapped: FBoundMapped.Aux[
	  DefaultRegistryTypes, CbRegistry, IForgeRegistryEntry,
	  CbRegistry[Item] :: CbRegistry[Block] :: CbRegistry[Enchantment] :: CbRegistry[Potion] :: HNil
	] = FBoundMapped[DefaultRegistryTypes, CbRegistry, IForgeRegistryEntry]

	protected type DefaultRegistries = defaultRegistriesMapped.Out
}