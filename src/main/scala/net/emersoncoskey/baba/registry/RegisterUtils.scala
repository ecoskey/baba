package net.emersoncoskey.baba.registry

import cats.free.Free
import cats.free.Free.liftF
import cats.syntax.flatMap._
import cats.syntax.traverse._

import net.minecraftforge.registries.{IForgeRegistryEntry, RegistryObject}

trait RegisterUtils {
	type Register[A] = Free[RegisterA, A]

	def declare[R <: IForgeRegistryEntry[R]: Registrable, A <: R](name: String, obj: => A): Register[RegistryObject[A]] =
		liftF[RegisterA, RegistryObject[A]](Declare[R, A](name, () => obj))

	def handleEvent(handler: EventHandler): Register[Unit] =
		liftF[RegisterA, Unit](HandleEvent(handler))

	// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	type DecMod[R <: IForgeRegistryEntry[R], A <: R] = RegistryObject[A] => EventHandler

	type SimpleDecMod[R <: IForgeRegistryEntry[R]] = DecMod[R, R]

	trait Attr[-I, R <: IForgeRegistryEntry[R], A <: R] { def :=(input: I): DecMod[R, A] }

	type SimpleAttr[-I, R <: IForgeRegistryEntry[R]] = Attr[I, R, R]

	def declareWithMods[R <: IForgeRegistryEntry[R]: Registrable, A <: R](name: String, obj: => A, mods: List[DecMod[R, A]]): Register[RegistryObject[A]] =
		declare[R, A](name, obj).flatTap(reg => mods.map(f => handleEvent(f(reg))).sequence)
}
