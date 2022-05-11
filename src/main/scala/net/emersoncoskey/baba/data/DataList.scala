package net.emersoncoskey.baba.data

import cats.Monoid
import net.minecraft.data.DataProvider

trait DataList
trait DNil extends DataList
case object DNil extends DNil
case class ::[H <: DataProvider: Monoid, T <: DataList](h: H, t: T) extends DataList

