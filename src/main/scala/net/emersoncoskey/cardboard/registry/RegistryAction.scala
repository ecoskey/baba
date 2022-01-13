package net.emersoncoskey.cardboard.registry

import cats.Monad
import cats.free.Free
import cats.free.Free.liftF
import net.minecraft.world.item.Item

import java.util.function.Supplier

type RegistryAction[A] = Free[RegistryActionA, A]

private sealed trait RegistryActionA[A]
private final case class RegisterItem[I <: Item](name: String, item: Supplier[I]) extends RegistryActionA[I]

object RegistryAction {
	def registerItem[I <: Item](name: String, item: Supplier[I]): RegistryAction[I] =
		liftF[RegistryActionA, I](RegisterItem(name, item))
}