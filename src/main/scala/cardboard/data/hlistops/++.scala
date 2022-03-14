package cardboard.data.hlistops

import cardboard.data.{::, HList, HNil}
import cardboard.syntax.hlist._
import net.minecraftforge.registries.IForgeRegistryEntry

trait ++[L <: HList, O <: HList] {
	type Result <: HList

	def apply(l: L, o: O): Result
}

object ++ {
	def apply[L <: HList, O <: HList](implicit plus: ++[L, O]): Aux[L, O, plus.Result] = plus

	type Aux[L <: HList, O <: HList, R <: HList] = ++[L, O] { type Result = R }

	implicit def basic[A <: IForgeRegistryEntry[A], O <: HList]: Aux[A :: HNil, O, A :: O] =
		new ++[A :: HNil, O] {
			type Result = A :: O

			override def apply(l: A :: HNil, o: O): Result = l.head :: o
		}

	implicit def inductive[A <: IForgeRegistryEntry[A], L <: HList, O <: HList](implicit plus: L ++ O): Aux[A :: L, O, A :: plus.Result] =
		new ++[A :: L, O] {
			override type Result = A :: plus.Result

			override def apply(l: A :: L, o: O): Result = l.head :: plus(l.tail, o)
		}
}
