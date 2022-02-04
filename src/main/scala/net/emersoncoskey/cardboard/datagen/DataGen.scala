package net.emersoncoskey.cardboard.datagen

import net.emersoncoskey.cardboard.registry.Reg
import net.minecraftforge.registries.IForgeRegistryEntry

trait DataGen[F[+_ <: A], A <: IForgeRegistryEntry[A]] extends Reg[F, A] {

}
