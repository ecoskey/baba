package net.emersoncoskey.cardboard.recipe

import cats.data.NonEmptyList
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient

import scala.language.implicitConversions

case class CbShapelessRecipe private(
	name       : String,
	ingredients: NonEmptyList[(Ingredient, Int)],
	result     : Item,
	count      : Int
) extends RecipeHaver {
	override def toFinishedRecipe: FinishedRecipe = ???
}

object CbShapelessRecipe {
	implicit def toTuple2[A](a: A): (A, Int) = (a, 1)

	def named(name: String): Builder.FirstStep = Builder.FirstStep(name)

	sealed trait Builder

	object Builder {
		case class FirstStep private(private val name: String) extends Builder {
			def ingredients(first: (Ingredient, Int), rest: (Ingredient, Int)*): FinalStep =
				Builder.FinalStep(name, NonEmptyList.of(first, rest: _*))
		}

		case class FinalStep private(
			private val name       : String,
			private val ingredients: NonEmptyList[(Ingredient, Int)],
		) extends Builder {
			def result(count: Int)(item: Item): CbShapelessRecipe = CbShapelessRecipe(name, ingredients, item, count)
		}
	}
}