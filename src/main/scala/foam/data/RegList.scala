package foam.data

import foam.registry.WrappedRegistry
import net.minecraftforge.registries.IForgeRegistryEntry

sealed trait RegList
sealed trait RNil extends RegList {
	def #:[O <: IForgeRegistryEntry[O]](o: WrappedRegistry[O]): O #: RNil = new #:(o, this)
}
case object RNil extends RNil
case class #:[A <: IForgeRegistryEntry[A], L <: RegList](head: WrappedRegistry[A], tail: L) extends RegList

