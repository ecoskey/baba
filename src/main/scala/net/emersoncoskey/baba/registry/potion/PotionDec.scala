package net.emersoncoskey.baba.registry.potion

import net.emersoncoskey.baba.registry.RegDec
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.item.alchemy.Potion

import java.util.function.Supplier

/** Used to declare a [[Potion]] to be added to the game */
class PotionDec[+P <: Potion] private(
	val name   : String,
	ctor   : Seq[MobEffectInstance] => P,
	effects: Seq[MobEffectInstance]
) extends RegDec[Potion] {
	lazy val supplier: Supplier[Potion] = () => ctor(effects)
}

object PotionDec {
	def apply(name: String, effects: MobEffectInstance*): PotionDec[Potion] = new PotionDec[Potion](name, new Potion(_: _*), effects)

	def apply[P <: Potion](name: String, ctor: Seq[MobEffectInstance] => P, effects: MobEffectInstance*) = new PotionDec[P](name, ctor, effects)
}
