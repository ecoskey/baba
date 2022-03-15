package foam.data

import foam.registry.{WrappedRegistry, RegDec}
import net.minecraftforge.registries.IForgeRegistryEntry

sealed trait DecList
sealed trait DNil extends DecList {
	def %:[A <: IForgeRegistryEntry[A]](o: Seq[RegDec[A]]): A %: DNil = new %:(o, this)
}
case object DNil extends DNil
case class %:[A <: IForgeRegistryEntry[A], L <: DecList](head: Seq[RegDec[A]], tail: L) extends DecList

