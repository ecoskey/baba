package cardboard.syntax

import cardboard.data.hlistops.++
import cardboard.data.{::, HList}
import cardboard.registry.CbRegistry
import net.minecraftforge.registries.IForgeRegistryEntry

trait HListSyntax {
	implicit class Ops[L <: HList](l: L) {
		def ::[A <: IForgeRegistryEntry[A]](a: CbRegistry[A]) = new ::(a, l)
		def ++[O <: HList](o: O)(implicit plus: L ++ O): plus.Result = plus(l, o)
	}
}
