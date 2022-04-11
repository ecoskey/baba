package net.emersoncoskey.baba

import cats.free.Free
import cats.free.Free.liftF
import cats.syntax.all._
import net.minecraftforge.registries.{IForgeRegistryEntry, RegistryObject}

import java.util.function.Supplier

package object registry {
	sealed trait RegisterA[A]
	case class Declare[A <: IForgeRegistryEntry[A]] private(name: String, supplier: Supplier[A]) extends RegisterA[RegistryObject[A]]
	case class HandleEvent private(handler: EventHandler) extends RegisterA[Unit]

	// FREE MONAD UTILS ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	type Register[A] = Free[RegisterA, A]

	def declare[A <: IForgeRegistryEntry[A]](name: String, supplier: Supplier[A]): Register[RegistryObject[A]] =
		liftF[RegisterA, RegistryObject[A]](Declare(name, supplier))

	def handleEvent(handler: EventHandler): Register[Unit] = liftF[RegisterA, Unit](HandleEvent(handler))

	// UTIL METHODS ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	def declareWithMods[A <: IForgeRegistryEntry[A]](name: String, supplier: Supplier[A], mods: List[A => EventHandler]): Register[RegistryObject[A]] =
		declare[A](name, supplier).flatTap(reg => mods.map(f => handleEvent(f(reg.get))).sequence)
}
