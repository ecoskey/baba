package net.emersoncoskey.baba.registry.declarations

import net.emersoncoskey.baba.registry._
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraftforge.registries.RegistryObject

trait SoundEventDecs {
	def soundEvent(modId: String, name: String): McAction[RegistryObject[SoundEvent]] =
		declare(name, new SoundEvent(new ResourceLocation(modId, name)))
}
