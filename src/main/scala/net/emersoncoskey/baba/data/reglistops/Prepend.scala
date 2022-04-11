package net.emersoncoskey.baba.data.reglistops

import net.emersoncoskey.baba.data.{#:, RNil, RegList}
import net.emersoncoskey.baba.registry.WrappedRegistry
import net.minecraftforge.registries.IForgeRegistryEntry
import net.emersoncoskey.baba.syntax.reglist._

trait Prepend[A <: IForgeRegistryEntry[A], R <: RegList] {
	type Out <: RegList
	def apply(a: WrappedRegistry[A], l: R): Out
}

object Prepend extends LowPriorityPrepend {
	type Aux[A <: IForgeRegistryEntry[A], R <: RegList, O <: RegList] = Prepend[A, R] { type Out = O }

	def basic[A <: IForgeRegistryEntry[A]]: Aux[A, RNil, A #: RNil] = new Prepend[A, RNil] {
		type Out = A #: RNil
		override def apply(a: WrappedRegistry[A], l: RNil): A #: RNil = a #: l
	}

	def inductive[A <: IForgeRegistryEntry[A], R <: RegList](implicit in: A In R): Aux[A, R, R] = new Prepend[A, R] {
		type Out = R
		override def apply(a: WrappedRegistry[A], l: R): Out = l
	}
}

trait LowPriorityPrepend {
	def inductiveLowPriority[A <: IForgeRegistryEntry[A], R <: RegList]: Prepend.Aux[A, R, A #: R] = new Prepend[A, R] {
		type Out = A #: R
		override def apply(a: WrappedRegistry[A], l: R): Out = a #: l
	}
}

