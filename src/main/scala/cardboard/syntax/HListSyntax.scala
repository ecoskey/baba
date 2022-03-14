package cardboard.syntax

import cardboard.data.hlistops.{++, get, in}
import cardboard.data.{::, HList}
import cardboard.registry.CbRegistry
import net.minecraftforge.registries.IForgeRegistryEntry

trait HListSyntax {
	implicit class Ops[L <: HList](l: L) {
		def ::[A <: IForgeRegistryEntry[A]](a: CbRegistry[A]): A :: L = new ::(a, l)

		def ++[O <: HList](o: O)(implicit plus: ++[L, O]): plus.Result = plus(l, o)

		def get[A <: IForgeRegistryEntry[A]](implicit in: A in L): CbRegistry[A] = in(l)

		def getOption[A <: IForgeRegistryEntry[A]](implicit get: L get A): Option[CbRegistry[A]] = get(l)
	}
}
