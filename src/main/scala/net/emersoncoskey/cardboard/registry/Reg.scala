package net.emersoncoskey.cardboard.registry

import net.emersoncoskey.cardboard.CbMod
import net.minecraftforge.registries.{IForgeRegistry, IForgeRegistryEntry}

trait Reg[-F[+_ <: R], R <: IForgeRegistryEntry[R]] {
	val registry: IForgeRegistry[R]

	def reg(r: F[R], mod: CbMod): RegistryDec[R]
}

object Reg {
	def apply[F[+_ <: R], R <: IForgeRegistryEntry[R]](implicit r: Reg[F, R]): Reg[F, R] = r

	implicit class Ops[-F[+_ <: R], R <: IForgeRegistryEntry[R]](fa: F[R])(implicit r: Reg[F, R]) {
		private[cardboard] def reg(mod: CbMod): RegistryDec[R] = r.reg(fa, mod)
	}
}
