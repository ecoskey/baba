package net.emersoncoskey.baba.ops

import cats.Monoid
import net.emersoncoskey.baba.data.{DataList, DNil, ::}
import net.minecraft.data.DataProvider

abstract class Insert[H <: DataProvider: Monoid, T <: DataList] {
	type Out <: DataList

	def insert(h: H, t: T): Out
}

object Insert {
	type Aux[H <: DataProvider: Monoid, T <: DataList, O <: DataList] = Insert[H, T]{ type Out <: DataList }

	implicit def basic[H <: DataProvider: Monoid]: Aux[H, DNil, H :: DNil] = new Insert[H, DNil] {
		type Out = H :: DNil

		override def insert(h: H, t: DNil): H :: DNil =
	}
}