package net.emersoncoskey.baba.registry.potion

import net.emersoncoskey.baba.registry.RegDec
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.item.alchemy.Potion

import java.util.function.Supplier

/** Used to declare a [[Potion]] to be added to the game */
class PotionDec private(
	val name: String,
	effects : Seq[MobEffectInstance]
) extends RegDec[Potion] {
	lazy val supplier: Supplier[Potion] = () => new Potion(effects:_*)
}

object PotionDec {
	def apply(name: String, effects: MobEffectInstance*): PotionDec = new PotionDec(name, effects)
}
