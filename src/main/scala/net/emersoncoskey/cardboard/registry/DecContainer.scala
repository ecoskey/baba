package net.emersoncoskey.cardboard.registry

import net.minecraftforge.registries.IForgeRegistryEntry

trait DecContainer {
	type F[+_ <: A]
	type A <: IForgeRegistryEntry[A]

	val fa: F[A]
}
