package cardboard.data

import cardboard.registry.CbRegistry
import net.minecraftforge.registries.IForgeRegistryEntry

sealed trait RegList
sealed trait RNil extends RegList {
	def #:[O <: IForgeRegistryEntry[O]](o: CbRegistry[O]): O #: RNil = new #:(o, this)
}
case object RNil extends RNil
case class #:[A <: IForgeRegistryEntry[A], L <: RegList](head: CbRegistry[A], tail: L) extends RegList

