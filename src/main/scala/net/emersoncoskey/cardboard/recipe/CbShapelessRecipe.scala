package net.emersoncoskey.cardboard.recipe

import cats.data.NonEmptyList
import com.google.gson.JsonObject
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.{Ingredient, RecipeSerializer}


case class CbShapelessRecipe(
	ingredients: NonEmptyList[(Ingredient, Int)],
	result     : Item,
	count      : Int
) extends FinishedRecipe {
	override def serializeRecipeData(pJson: JsonObject): Unit = ???

	override def getId: ResourceLocation = ???

	override def getType: RecipeSerializer[_] = RecipeSerializer.SHAPELESS_RECIPE

	override def serializeAdvancement(): JsonObject = ???

	override def getAdvancementId: ResourceLocation = ???
}

object CbShapelessRecipe {
	def apply(firstIngredient: (Ingredient, Int), restIngredients: (Ingredient, Int)*)
	  (count: Int)(result: Item): CbShapelessRecipe =
		new CbShapelessRecipe(NonEmptyList.of(firstIngredient, restIngredients: _*), result, count)

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