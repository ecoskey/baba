package baba.data.reglistops

import baba.data.{#:, RegList}
import baba.registry.WrappedRegistry
import net.minecraftforge.registries.IForgeRegistryEntry

trait In[A <: IForgeRegistryEntry[A], L <: RegList] extends (L => WrappedRegistry[A]) {
	def get(l: L): WrappedRegistry[A]
	override def apply(l: L): WrappedRegistry[A] = get(l)
}

object In {
	def apply[A <: IForgeRegistryEntry[A], S <: RegList](implicit in: A In S): A In S = in

	implicit def basic[A <: IForgeRegistryEntry[A], L <: RegList]: A In (A #: L) =
		(l: A #: L) => l.head

	implicit def inductive[A <: IForgeRegistryEntry[A], B <: IForgeRegistryEntry[B], L <: RegList](implicit in: A In L): A In (B #: L) =
		(l: B #: L) => in.get(l.tail)
}


