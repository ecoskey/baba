package cardboard.data

import cardboard.registry.CbRegistry
import net.minecraftforge.registries.IForgeRegistryEntry

sealed trait RegList
sealed trait RegNil extends RegList {
	def #:[O <: IForgeRegistryEntry[O]](o: CbRegistry[O]): O #: RegNil = new #:(o, this)
}
case object RegNil extends RegNil
case class #:[A <: IForgeRegistryEntry[A], L <: RegList](head: CbRegistry[A], tail: L) extends RegList

