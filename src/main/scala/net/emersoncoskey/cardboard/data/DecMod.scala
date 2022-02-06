package net.emersoncoskey.cardboard.data

import net.emersoncoskey.cardboard.registry.RegistryDec
import net.minecraftforge.registries.IForgeRegistryEntry

trait DecMod[A <: IForgeRegistryEntry[A]] extends (RegistryDec[A] => RegistryDec[A])