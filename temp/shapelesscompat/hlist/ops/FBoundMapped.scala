package cardboard.shapelesscompat.hlist.ops

import shapeless.{HList, HNil, ::}

trait FBoundMapped[In <: HList, F[X <: G[X]], G[_]] {
	type Out <: HList
}

object FBoundMapped {
	def apply[I <: HList, F[X <: G[X]], G[_]](implicit ev: FBoundMapped[I, F, G]): Aux[I, F, G, ev.Out] = ev

	type Aux[I <: HList, F[X <: G[X]], G[_], O <: HList] = FBoundMapped[I, F, G] { type Out = O }

	implicit def basic[F[X <: G[X]], G[_]]: Aux[HNil, F, G, HNil] = new FBoundMapped[HNil, F, G] { type Out = HNil }

	implicit def inductive[A <: G[A], H <: HList, F[X <: G[X]], G[_]](implicit ev: FBoundMapped[H, F, G]): Aux[A :: H, F, G, F[A] :: ev.Out] =
		new FBoundMapped[A :: H, F, G] { type Out = F[A] :: ev.Out }
}
