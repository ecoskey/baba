package cardboard.syntax

import cardboard.CbMod
import cardboard.registry.{Reg, RegistryDec}
import net.minecraftforge.registries.IForgeRegistryEntry

trait RegSyntax {
	implicit class RegOps[-F[+_ <: R], R <: IForgeRegistryEntry[R]](fa: F[R])(implicit r: Reg[F, R]) {
		private[cardboard] def reg(mod: CbMod): RegistryDec[R] = r.reg(fa, mod)
	}
}
