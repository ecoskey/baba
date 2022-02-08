package net.emersoncoskey.cardboard.datagen

import net.minecraftforge.registries.IForgeRegistryEntry

trait DecMod[R <: IForgeRegistryEntry[R]] extends (R => DataContainer) {
	
}
