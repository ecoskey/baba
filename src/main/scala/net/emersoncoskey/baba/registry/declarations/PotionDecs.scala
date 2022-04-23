package net.emersoncoskey.baba.registry.declarations

import net.emersoncoskey.baba.registry.{Register, declare}
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.item.alchemy.Potion
import net.minecraftforge.registries.RegistryObject

trait PotionDecs {
	def potion(name: String, effects: MobEffectInstance*): Register[RegistryObject[Potion]] = declare(name, new Potion(name, effects:_*))
}
