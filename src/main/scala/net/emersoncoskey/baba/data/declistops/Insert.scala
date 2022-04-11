package net.emersoncoskey.baba.data.declistops

import net.emersoncoskey.baba.data.{%:, DNil, DecList}
import net.emersoncoskey.baba.syntax.declist._
import net.emersoncoskey.baba.registry.RegDec
import net.minecraftforge.registries.IForgeRegistryEntry

trait Insert[A <: IForgeRegistryEntry[A], D <: DecList] {
	type Out <: DecList

	def apply(a: Seq[RegDec[A]], l: D): Out
}

object Insert {
	type Aux[A <: IForgeRegistryEntry[A], D <: DecList, O <: DecList] = Insert[A, D] { type Out = O }

	def basic[A <: IForgeRegistryEntry[A]]: Aux[A, DNil, A %: DNil] = new Insert[A, DNil] {
		type Out = A %: DNil
		def apply(a: Seq[RegDec[A]], l: DNil): Out = a %: l
	}

	def inductiveEq[A <: IForgeRegistryEntry[A], D <: DecList]: Aux[A, A %: D, A %: D] = new Insert[A, A %: D] {
		type Out = A %: D

		override def apply(a: Seq[RegDec[A]], l: A %: D): Out = (a ++ l.head) %: l.tail
	}

	def inductiveNeq[A <: IForgeRegistryEntry[A], B <: IForgeRegistryEntry[B], D <: DecList]
	  (implicit ev: Insert[A, D]): Aux[A, B %: D, B %: ev.Out] = new Insert[A, B %: D] {
		type Out = B %: ev.Out

		def apply(a: Seq[RegDec[A]], l: B %: D): Out = l.head %: ev(a, l.tail)
	}
}