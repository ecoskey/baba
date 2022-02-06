package net.emersoncoskey.cardboard.registry

import net.minecraftforge.registries.{IForgeRegistry, IForgeRegistryEntry}

trait Reg[F[+_ <: A], A <: IForgeRegistryEntry[A]] {
	val registry: IForgeRegistry[A]

	def reg(r: F[A]): RegistryDec[A]
}

object Reg {
	def apply[F[+_ <: A], A <: IForgeRegistryEntry[A]](implicit r: Reg[F, A]): Reg[F, A] = r

	implicit class Ops[F[+_ <: A], A <: IForgeRegistryEntry[A]](fa: F[A])(implicit r: Reg[F, A]) {
		private[cardboard] def reg: RegistryDec[A] = r.reg(fa)
	}
}
