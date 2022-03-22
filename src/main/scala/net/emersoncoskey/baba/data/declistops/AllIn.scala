package net.emersoncoskey.baba.data.declistops

import net.emersoncoskey.baba.data.declistops.AllIn.PolyConsumer
import net.emersoncoskey.baba.data.reglistops.In
import net.emersoncoskey.baba.data.{%:, DNil, DecList, RegList}
import net.emersoncoskey.baba.registry.{RegDec, WrappedRegistry}
import net.minecraftforge.registries.IForgeRegistryEntry

/** A type used to prove that all elements of D are in R */
trait AllIn[D <: DecList, R <: RegList] extends ((D, R, PolyConsumer) => Unit)

object AllIn {
	trait PolyConsumer {
		def apply[A <: IForgeRegistryEntry[A]](a: Seq[RegDec[A]], registry: WrappedRegistry[A]): Unit
	}

	implicit def basic[R <: RegList]: AllIn[DNil, R] = (_, _, _) => ()
	implicit def inductive[A <: IForgeRegistryEntry[A], D <: DecList, R <: RegList]
	  (implicit tailEv: D AllIn R, in: A In R): (A %: D) AllIn R =
		(l, r, fn) => {
			fn(l.head, in(r))
			tailEv(l.tail, r, fn)
		}
}
