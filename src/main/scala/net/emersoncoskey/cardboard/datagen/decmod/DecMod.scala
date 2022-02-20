package net.emersoncoskey.cardboard.datagen.decmod

import cats.Monad
import cats.data.Chain
import cats.implicits._
import net.emersoncoskey.cardboard.CbMod
import net.minecraft.world.level.block.Block
import net.minecraftforge.registries.IForgeRegistryEntry

final case class DecMod[R <: IForgeRegistryEntry[R], +O] private(run: R => (Chain[CbMod.EventListener], O)) extends AnyVal

object DecMod {
	//type MapCtor[R <: IForgeRegistryEntry[R]] = ({type T[+O] = DecMod[R, O]})#T

	implicit def monadInstance[R <: IForgeRegistryEntry[R]]: Monad[({type T[+O] = DecMod[R, O]})#T] = new Monad[({type T[+O] = DecMod[R, O]})#T] {
		override def pure[A](x: A): DecMod[R, A] = DecMod((_: R) => (Chain.nil, x))

		override def flatMap[A, B](fa: DecMod[R, A])(f: A => DecMod[R, B]): DecMod[R, B] = DecMod((r: R) => {
			val (providers1, res) = fa.run(r)
			val (providers2, res2) = f(res).run(r)
			(providers1 ++ providers2, res2)
		})

		override def tailRecM[A, B](a: A)(f: A => DecMod[R, Either[A, B]]): DecMod[R, B] = ??? //TODO: what even is this
	}

	def none[R <: IForgeRegistryEntry[R]]: DecMod[R, Unit] = pure(())

	def pure[R <: IForgeRegistryEntry[R], A](a: A): DecMod[R, A] = monadInstance.pure(a)

	def listener[R <: IForgeRegistryEntry[R], A](f: R => CbMod.EventListener): DecMod[R, Unit] = new DecMod(r => (Chain.one(f(r)), ()))

	/*def apply[R <: IForgeRegistryEntry[R], A](f: R => (Option[CbMod.EventListener], A)): DecMod[R, A] = new DecMod(r => {
		val (providerOption, res) = f(r)
		val providers = providerOption match {
			case Some(p) => Chain.one(p)
			case None => Chain.nil
		}
		(providers, res)
	})*/
}