package net.emersoncoskey.baba.registry

import net.emersoncoskey.baba.BaseMod
import net.minecraftforge.registries.IForgeRegistryEntry

trait Registrable[A <: IForgeRegistryEntry[A]] {
	def getRegistry(mod: BaseMod): WrappedRegistry[A]
}
