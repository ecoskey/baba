package cardboard.data.reglistops

import cardboard.data.{#:, RegList}
import cardboard.registry.CbRegistry
import net.minecraftforge.registries.IForgeRegistryEntry

trait In[A <: IForgeRegistryEntry[A], L <: RegList] extends (L => CbRegistry[A]) {
	def get(l: L): CbRegistry[A]
	override def apply(l: L): CbRegistry[A] = get(l)
}

object In {
	def apply[A <: IForgeRegistryEntry[A], S <: RegList](implicit in: A In S): A In S = in

	implicit def basic[A <: IForgeRegistryEntry[A], L <: RegList]: A In (A #: L) =
		(l: A #: L) => l.head

	implicit def inductive[A <: IForgeRegistryEntry[A], B <: IForgeRegistryEntry[B], L <: RegList](implicit in: A In L): A In (B #: L) =
		(l: B #: L) => in.get(l.tail)
}


