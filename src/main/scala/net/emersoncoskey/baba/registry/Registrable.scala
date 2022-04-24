package net.emersoncoskey.baba.registry

import net.minecraft.core.{Registry, RegistryAccess}
import net.minecraft.resources.ResourceKey
import net.minecraftforge.registries.{ForgeRegistry, IForgeRegistryEntry, RegistryManager}

class Registrable[R <: IForgeRegistryEntry[R]](val key: ResourceKey[Registry[R]], val objClass: Class[R]) {
	def getForgeRegistry: ForgeRegistry[R] = RegistryManager.ACTIVE.getRegistry(key)
	def getVanillaRegistry: Registry[R] = RegistryAccess.BUILTIN.get.registryOrThrow(key)
}

object Registrable {
	def apply[R <: IForgeRegistryEntry[R]](implicit registrable: Registrable[R]): Registrable[R] = registrable
}
