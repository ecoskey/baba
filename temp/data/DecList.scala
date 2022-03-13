package cardboard.data

import cardboard.registry.CbRegistry
import net.minecraftforge.registries.IForgeRegistryEntry

sealed trait DecList
sealed trait DNil extends DecList {
	def %:[O <: IForgeRegistryEntry[O]](o: CbRegistry[O]): O %: DNil = new %:(o, this)
}
case object DNil extends DNil
case class %:[A <: IForgeRegistryEntry[A], L <: DecList](head: CbRegistry[A], tail: L) extends DecList

