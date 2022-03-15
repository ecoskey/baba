package foam.shapelesscompat.hlist.contraints

import shapeless.{::, HList, HNil}


trait FConstraint[H <: HList, F[_]]

object FConstraint {
	implicit def basic[F[_]]: FConstraint[HNil, F] = new FConstraint[HNil, F] {}
	implicit def inductive[A <: F[A], H <: HList, F[_]](implicit ev: FConstraint[H, F]): FConstraint[A :: H, F] = new FConstraint[A :: H, F] {}
}
