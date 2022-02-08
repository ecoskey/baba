package net.emersoncoskey.cardboard.registry

import net.emersoncoskey.cardboard.datagen.DecMod
import net.minecraftforge.registries.IForgeRegistryEntry

import java.util.function.Supplier

case class RegistryDec[A <: IForgeRegistryEntry[A]](name: String, sup: Supplier[A], mods: Seq[DecMod[A]])
