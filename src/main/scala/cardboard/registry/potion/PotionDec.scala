package cardboard.registry.potion

import cardboard.registry.RegDec
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.item.alchemy.Potion

import java.util.function.Supplier

class PotionDec[+P <: Potion] private(
	val name   : String,
	val ctor   : Seq[MobEffectInstance] => P,
	val effects: Seq[MobEffectInstance]
) extends RegDec[Potion] {
	lazy val sup: Supplier[Potion] = () => ctor(effects)
}

object PotionDec {
	def apply(name: String, effects: MobEffectInstance*): PotionDec[Potion] = new PotionDec[Potion](name, new Potion(_: _*), effects)

	def apply[P <: Potion](name: String, ctor: Seq[MobEffectInstance] => P, effects: MobEffectInstance*) = new PotionDec[P](name, ctor, effects)
}
