package cardboard.data.registrylistops

import cardboard.data.{#:, RegistryList, RNil}
import cardboard.syntax.hlist._
import net.minecraftforge.registries.IForgeRegistryEntry

trait ++[L <: RegistryList, O <: RegistryList] {
	type Result <: RegistryList

	def apply(l: L, o: O): Result
}

object ++ {
	def apply[L <: RegistryList, O <: RegistryList](implicit plus: ++[L, O]): Plus[L, O, plus.Result] = plus

	type Plus[L <: RegistryList, O <: RegistryList, R <: RegistryList] = ++[L, O] { type Result = R }

	implicit def basic[A <: IForgeRegistryEntry[A], O <: RegistryList]: Plus[A #: RNil, O, A #: O] =
		new ++[A #: RNil, O] {
			type Result = A #: O

			override def apply(l: A #: RNil, o: O): Result = l.head #: o
		}

	implicit def inductive[A <: IForgeRegistryEntry[A], L <: RegistryList, O <: RegistryList](implicit plus: L ++ O): Plus[A #: L, O, A #: plus.Result] =
		new ++[A #: L, O] {
			override type Result = A #: plus.Result

			override def apply(l: A #: L, o: O): Result = l.head #: plus(l.tail, o)
		}
}
