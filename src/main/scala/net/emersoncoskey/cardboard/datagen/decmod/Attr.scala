package net.emersoncoskey.cardboard.datagen.decmod

import net.minecraftforge.registries.IForgeRegistryEntry

trait Attr[R <: IForgeRegistryEntry[R], -I,  +A] {
	def :=(i: I): DecMod[R, A]
}

