package baba.datagen.recipe.furnace

import baba.datagen.recipe.BabaRecipeBuilderRecipe
import cats.data.State
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.{Ingredient, RecipeSerializer}

class BabaFurnaceRecipe private(
	internal: SimpleCookingRecipeBuilder,
	act     : State[SimpleCookingRecipeBuilder, _],
	id      : Option[String] = None,
) extends BabaRecipeBuilderRecipe[SimpleCookingRecipeBuilder](internal, act, id)

object BabaFurnaceRecipe extends BabaRecipeBuilderRecipe.Ops[SimpleCookingRecipeBuilder] {
	def campfire(ingredient: Ingredient, result: Item, exp: Float, time: Int, id: Option[String] = None)
	  (act: State[SimpleCookingRecipeBuilder, _]): BabaFurnaceRecipe = {
		new BabaFurnaceRecipe(
			SimpleCookingRecipeBuilder.cooking(
				ingredient, result, exp, time, RecipeSerializer.CAMPFIRE_COOKING_RECIPE
			), act, id
		)
	}

	def blasting(ingredient: Ingredient, result: Item, exp: Float, time: Int, id: Option[String] = None)
	  (act: State[SimpleCookingRecipeBuilder, _]): BabaFurnaceRecipe =
		new BabaFurnaceRecipe(
			SimpleCookingRecipeBuilder.cooking(
				ingredient, result, exp, time, RecipeSerializer.BLASTING_RECIPE
			), act, id
		)

	def smelting(ingredient: Ingredient, result: Item, exp: Float, time: Int, id: Option[String] = None)
	  (act: State[SimpleCookingRecipeBuilder, _]): BabaFurnaceRecipe =
		new BabaFurnaceRecipe(
			SimpleCookingRecipeBuilder.cooking(
				ingredient, result, exp, time, RecipeSerializer.SMELTING_RECIPE
			), act, id
		)

	def smoking(ingredient: Ingredient, result: Item, exp: Float, time: Int, id: Option[String] = None)
	  (act: State[SimpleCookingRecipeBuilder, _]): BabaFurnaceRecipe =
		new BabaFurnaceRecipe(
			SimpleCookingRecipeBuilder.cooking(
				ingredient, result, exp, time, RecipeSerializer.SMOKING_RECIPE
			), act, id
		)
}
