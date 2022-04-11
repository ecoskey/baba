package net.emersoncoskey.baba.registry


import net.minecraftforge.registries.{IForgeRegistryEntry, RegistryObject}

/** a Registry Declaration */
case class RegDec[A <: IForgeRegistryEntry[A]](registryObject: RegistryObject[A], registration: Register[RegistryObject[A]])