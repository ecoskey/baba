package cardboard.data.hlistops

import cardboard.data.{::, HList}
import cardboard.registry.CbRegistry
import net.minecraftforge.registries.IForgeRegistryEntry

trait in[A <: IForgeRegistryEntry[A], L <: HList] {
	def get(hlist: L): CbRegistry[A]
}

object in {
	def apply[A <: IForgeRegistryEntry[A], S <: HList](implicit in: A in S): A in S = in

	implicit def basic[A <: IForgeRegistryEntry[A], L <: HList]: A in (A :: L) =
		(hlist: A :: L) => hlist.head

	implicit def inductive[A <: IForgeRegistryEntry[A], B <: IForgeRegistryEntry[B], L <: HList](implicit in: A in L): A in (B :: L) =
		(hlist: B :: L) => in.get(hlist.tail)
}


