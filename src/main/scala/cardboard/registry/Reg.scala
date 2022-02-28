package cardboard.registry

import cardboard.CbMod
import net.minecraftforge.registries.{IForgeRegistry, IForgeRegistryEntry}

trait Reg[-F[+_ <: R], R <: IForgeRegistryEntry[R]] {
	val registry: IForgeRegistry[R]

	def reg(r: F[R], mod: CbMod): RegistryDec[R]
}

object Reg {
	def apply[F[+_ <: R], R <: IForgeRegistryEntry[R]](implicit r: Reg[F, R]): Reg[F, R] = r
}
