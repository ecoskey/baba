package cardboard.data

import cardboard.registry.CbRegistry
import net.minecraftforge.registries.IForgeRegistryEntry

sealed trait HList
sealed trait HNil extends HList {
	def ::[O <: IForgeRegistryEntry[O]](o: CbRegistry[O]): O :: HNil = new ::(o, this)
}
case object HNil extends HNil
case class ::[A <: IForgeRegistryEntry[A], L <: HList](head: CbRegistry[A], tail: L) extends HList