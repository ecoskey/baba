package net.emersoncoskey.cardboard.recipe

import cats.data.NonEmptyList
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient


case class CbShapelessRecipe(ingredients: NonEmptyList[(Ingredient, Int)], result: Item, count: Int)

object CbShapelessRecipe {
	def apply(firstIngredient: (Ingredient, Int), restIngredients: (Ingredient, Int)*)
	         (count: Int)(result: Item): CbShapelessRecipe =
		new CbShapelessRecipe(NonEmptyList.of(firstIngredient, restIngredients:_*), result, count)

	/*def apply(firstIngredient: (Item, Int), restIngredients: (Item, Int)*)
	         (count: Int)(result: Item): CbShapelessRecipe = {
		val first = (Ingredient.of(firstIngredient._1), firstIngredient._2)
		val rest = restIngredients.map(i => (Ingredient.of(i._1), i._2))
		CbShapelessRecipe(first, rest:_*)(count)(result)
	}*/

	val test =
		CbShapelessRecipe(
			null -> 2,
			null -> 2,
			null -> 2,
		)(2)(null)
}