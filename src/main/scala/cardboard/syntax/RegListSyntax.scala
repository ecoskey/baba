package cardboard.syntax

import cardboard.data.reglistops.{++, in}
import cardboard.data.{#:, RegList}
import cardboard.registry.CbRegistry
import net.minecraftforge.registries.IForgeRegistryEntry

trait RegListSyntax {
	implicit class RegListOps[L <: RegList](l: L) {
		def #:[A <: IForgeRegistryEntry[A]](a: CbRegistry[A]): A #: L = new #:(a, l)

		def ++[O <: RegList](o: O)(implicit plus: ++[L, O]): plus.Result = plus(l, o)

		def get[A <: IForgeRegistryEntry[A]](implicit in: A in L): CbRegistry[A] = in(l)
	}
}
