package net.emersoncoskey.baba.registry

import cats.free.Free
import cats.free.Free.liftF
import cats.syntax.flatMap._
import cats.syntax.traverse._

import net.minecraftforge.registries.{IForgeRegistryEntry, RegistryObject}

trait RegisterApi {
	type McAction[A] = Free[McActionA, A]

	def declare[R <: IForgeRegistryEntry[R]: Registrable, A <: R](name: String, obj: => A): McAction[RegistryObject[A]] =
		liftF[McActionA, RegistryObject[A]](Declare[R, A](name, () => obj))

	def handleEvent(handler: EventHandler): McAction[Unit] =
		liftF[McActionA, Unit](HandleEvent(handler))

	// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	type DecMod[R <: IForgeRegistryEntry[R], -A <: R] = RegistryObject[_ <: A] => EventHandler

	type SimpleDecMod[R <: IForgeRegistryEntry[R]] = DecMod[R, R]

	trait Attr[-I, R <: IForgeRegistryEntry[R], A <: R] { def :=(input: I): DecMod[R, A] }

	type SimpleAttr[-I, R <: IForgeRegistryEntry[R]] = Attr[I, R, R]

	def declareWithMods[R <: IForgeRegistryEntry[R]: Registrable, A <: R](name: String, obj: => A, mods: DecMod[R, A]*): McAction[RegistryObject[A]] =
		declare[R, A](name, obj).flatTap(reg => mods.map(f => handleEvent(f(reg))).sequence)
}
