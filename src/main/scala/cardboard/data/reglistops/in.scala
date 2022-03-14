package cardboard.data.reglistops

import cardboard.data.{#:, RegList}
import cardboard.registry.CbRegistry
import net.minecraftforge.registries.IForgeRegistryEntry

trait in[A <: IForgeRegistryEntry[A], L <: RegList] extends (L => CbRegistry[A]) {
	def get(l: L): CbRegistry[A]
	override def apply(l: L): CbRegistry[A] = get(l)
}

object in {
	def apply[A <: IForgeRegistryEntry[A], S <: RegList](implicit in: A in S): A in S = in

	implicit def basic[A <: IForgeRegistryEntry[A], L <: RegList]: A in (A #: L) =
		(l: A #: L) => l.head

	implicit def inductive[A <: IForgeRegistryEntry[A], B <: IForgeRegistryEntry[B], L <: RegList](implicit in: A in L): A in (B #: L) =
		(l: B #: L) => in.get(l.tail)
}


