package cardboard.data

import cardboard.registry.{CbRegistry, RegDec}
import net.minecraftforge.registries.IForgeRegistryEntry
import cats.syntax.option._

sealed trait HList {
	def get(dec: RegDec): Option[CbRegistry[dec.A]]
}
sealed trait HNil extends HList {
	def ::[O <: IForgeRegistryEntry[O]](o: CbRegistry[O]): O :: HNil = new ::(o, this)
	def get(dec: RegDec): Option[CbRegistry[dec.A]] = None
}
case object HNil extends HNil
case class ::[A <: IForgeRegistryEntry[A], L <: HList](head: CbRegistry[A], tail: L) extends HList {
	def get(dec: RegDec)(implicit eq: CbRegistry[A] =:= CbRegistry[dec.A]): Option[CbRegistry[dec.A]] = eq(head).some
	def get(dec: RegDec): Option[CbRegistry[dec.A]] = tail.get(dec)
}

