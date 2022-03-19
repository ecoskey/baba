package baba.syntax

import baba.data.reglistops.{++, In}
import baba.data.{#:, RegList}
import baba.registry.WrappedRegistry
import net.minecraftforge.registries.IForgeRegistryEntry

trait RegListSyntax {
	implicit class RegListOps[L <: RegList](l: L) {
		def #:[A <: IForgeRegistryEntry[A]](a: WrappedRegistry[A]): A #: L = new #:(a, l)

		def ++[O <: RegList](o: O)(implicit plus: ++[L, O]): plus.Result = plus(l, o)

		def get[A <: IForgeRegistryEntry[A]](implicit in: A In L): WrappedRegistry[A] = in(l)
	}
}
