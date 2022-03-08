package cardboard.registry.potion

import cardboard.CbMod
import cardboard.registry.{Reg, RegistryDec}
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.item.alchemy.Potion
import net.minecraftforge.registries.{ForgeRegistries, IForgeRegistry}

class PotionDec[+P <: Potion] private(
	val name   : String,
	val ctor   : Seq[MobEffectInstance] => P,
	val effects: Seq[MobEffectInstance]
)

object PotionDec {
	/*implicit val regInstance: Reg[PotionDec, Potion] = new Reg[PotionDec, Potion] {
		val registry: IForgeRegistry[Potion] = ForgeRegistries.POTIONS

		def reg(r: PotionDec[Potion], mod: CbMod): RegistryDec[Potion] = RegistryDec(r.name, () => r.ctor(r.effects))
	}
*/
	def apply(name: String, effects: MobEffectInstance*): PotionDec[Potion] = new PotionDec[Potion](name, new Potion(_: _*), effects)

	def apply[P <: Potion](name: String, ctor: Seq[MobEffectInstance] => P, effects: MobEffectInstance*) = new PotionDec[P](name, ctor, effects)
}
