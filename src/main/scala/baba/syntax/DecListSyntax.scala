package baba.syntax

import baba.data.declistops.AllIn
import baba.data.{%:, DecList, RegList}
import baba.registry.RegDec
import net.minecraftforge.registries.IForgeRegistryEntry

trait DecListSyntax {
	implicit class DecListOps[L <: DecList](l: L) {
		def %:[A <: IForgeRegistryEntry[A]](a: Seq[RegDec[A]]): A %: L = new %:(a, l)
		def foreach[R <: RegList](regList: R, fn: AllIn.PolyConsumer)(implicit ev: L AllIn R): Unit = ev(l, regList, fn)
	}
}
