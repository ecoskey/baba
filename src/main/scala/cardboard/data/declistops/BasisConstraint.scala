package cardboard.data.declistops

import cardboard.data.declistops.BasisConstraint.PolyConsumer
import cardboard.data.reglistops.in
import cardboard.data.{%:, DNil, DecList, RegList}
import cardboard.registry.{CbRegistry, RegDec}
import net.minecraftforge.registries.IForgeRegistryEntry

trait BasisConstraint[D <: DecList, R <: RegList] extends ((D, R, PolyConsumer) => Unit)

object BasisConstraint {
	trait Basis[R <: RegList] {
		type T[D <: DecList] = BasisConstraint[D, R]
	}

	trait PolyConsumer {
		def apply[A <: IForgeRegistryEntry[A]](a: Seq[RegDec[A]], registry: CbRegistry[A]): Unit
	}

	implicit def basic[R <: RegList]: BasisConstraint[DNil, R] = (_, _, _) => ()
	implicit def inductive[A <: IForgeRegistryEntry[A], D <: DecList, R <: RegList]
	  (implicit tailEv: BasisConstraint[D, R], in: A in R): BasisConstraint[A %: D, R] =
		(l, r, fn) => {
			fn(l.head, in(r))
			tailEv(l.tail, r, fn)
		}

}
