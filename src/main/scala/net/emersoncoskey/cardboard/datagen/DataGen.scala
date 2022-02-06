package net.emersoncoskey.cardboard.datagen

import net.emersoncoskey.cardboard.registry.Reg
import net.minecraftforge.registries.IForgeRegistryEntry

//unused for now, going to possibly be used for a rewrite to allow more customization and custom registries
trait DataGen[F[-_ <: A], A <: IForgeRegistryEntry[A]] extends Reg[F, A] {

}

object DataGen {
	implicit class Ops[F[-_ <: A], A <: IForgeRegistryEntry[A]](fa: F[A])(implicit d: DataGen[F, A]) {

	}
}
