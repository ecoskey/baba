package net.emersoncoskey.baba.syntax

import net.emersoncoskey.baba.data.reglistops.{In, Prepend}
import net.emersoncoskey.baba.data.{#:, RegList}
import net.emersoncoskey.baba.registry.WrappedRegistry
import net.minecraftforge.registries.IForgeRegistryEntry

trait RegListSyntax {
	implicit class RegListOps[L <: RegList](l: L) {
		def #:[A <: IForgeRegistryEntry[A]](a: WrappedRegistry[A]): A #: L = new #:(a, l)

		def get[A <: IForgeRegistryEntry[A]](implicit in: A In L): WrappedRegistry[A] = in(l)

		def +:[A <: IForgeRegistryEntry[A]](a: WrappedRegistry[A])(implicit prepend: Prepend[A, L]): prepend.Out = prepend(a, l)
	}
}
