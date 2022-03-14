package cardboard.syntax

import cardboard.data.declistops.BasisConstraint
import cardboard.data.{%:, DecList, RegList}
import cardboard.registry.RegDec
import net.minecraftforge.registries.IForgeRegistryEntry

trait DecListSyntax {
	implicit class DecListOps[L <: DecList](l: L) {
		def %:[A <: IForgeRegistryEntry[A]](a: Seq[RegDec[A]]): A %: L = new %:(a, l)
		def foreach[R <: RegList](regList: R, fn: BasisConstraint.PolyConsumer)(implicit basis: BasisConstraint[L, R]): Unit = basis(l, regList, fn)
	}
}
