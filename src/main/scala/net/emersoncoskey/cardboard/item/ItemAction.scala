package net.emersoncoskey.cardboard.item

import cats.Monad
import net.minecraft.world.item.Item

class ItemAction[+A] private (a: => A) {
	def run: A = a
}

object ItemAction {
	implicit object ItemActionMonad extends Monad[ItemAction] {
		override def pure[A](a: A): ItemAction[A] = ItemAction(a)

		override def flatMap[A, B](fa: ItemAction[A])(f: A => ItemAction[B]): ItemAction[B] = ItemAction {
			val res1 = fa.run
			f(res1).run
		}

		override def tailRecM[A, B](a: A)(f: A => ItemAction[Either[A, B]]): ItemAction[B] = ??? //don't use this, dunno why it's here
	}

	def apply[A](a: => A): ItemAction[A] = new ItemAction(a)

	def register[A <: Item](item: A): ItemAction[A] = ???
}
