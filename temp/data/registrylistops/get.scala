package cardboard.data.registrylistops

import cardboard.data.{#:, RegistryList, RNil}
import cardboard.registry.CbRegistry
import cats.syntax.option._
import net.minecraftforge.registries.IForgeRegistryEntry

trait get[L <: RegistryList, A <: IForgeRegistryEntry[A]] extends (L => Option[CbRegistry[A]]) {
	def get(l: L): Option[CbRegistry[A]]
	override def apply(l: L): Option[CbRegistry[A]] = get(l)
}

object get extends LowPriorityGet {
	def apply[L <: RegistryList, A <: IForgeRegistryEntry[A]](implicit get: L get A): L get A = get

	implicit def basic[A <: IForgeRegistryEntry[A]]: RNil get A = _ => None

	implicit def inductiveEq[A <: IForgeRegistryEntry[A], L <: RegistryList]: (A #: L) get A = _.head.some
}

trait LowPriorityGet {
	implicit def inductiveNeq[A <: IForgeRegistryEntry[A], B <: IForgeRegistryEntry[B], L <: RegistryList]
	  (implicit getInTail: L get A): (B #: L) get A = l => getInTail(l.tail)
}





