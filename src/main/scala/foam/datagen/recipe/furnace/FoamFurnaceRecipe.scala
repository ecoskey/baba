package foam.datagen.recipe.furnace

import foam.datagen.recipe.FoamRecipeBuilderRecipe
import cats.data.State
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.{Ingredient, RecipeSerializer}

class FoamFurnaceRecipe private(
	internal: SimpleCookingRecipeBuilder,
	act     : State[SimpleCookingRecipeBuilder, _],
	id      : Option[String] = None,
) extends FoamRecipeBuilderRecipe[SimpleCookingRecipeBuilder](internal, act, id)

object FoamFurnaceRecipe extends FoamRecipeBuilderRecipe.Ops[SimpleCookingRecipeBuilder] {
	def campfire(ingredient: Ingredient, result: Item, exp: Float, time: Int, id: Option[String] = None)
	  (act: State[SimpleCookingRecipeBuilder, _]): FoamFurnaceRecipe = {
		new FoamFurnaceRecipe(
			SimpleCookingRecipeBuilder.cooking(
				ingredient, result, exp, time, RecipeSerializer.CAMPFIRE_COOKING_RECIPE
			), act, id
		)
	}

	def blasting(ingredient: Ingredient, result: Item, exp: Float, time: Int, id: Option[String] = None)
	  (act: State[SimpleCookingRecipeBuilder, _]): FoamFurnaceRecipe =
		new FoamFurnaceRecipe(
			SimpleCookingRecipeBuilder.cooking(
				ingredient, result, exp, time, RecipeSerializer.BLASTING_RECIPE
			), act, id
		)

	def smelting(ingredient: Ingredient, result: Item, exp: Float, time: Int, id: Option[String] = None)
	  (act: State[SimpleCookingRecipeBuilder, _]): FoamFurnaceRecipe =
		new FoamFurnaceRecipe(
			SimpleCookingRecipeBuilder.cooking(
				ingredient, result, exp, time, RecipeSerializer.SMELTING_RECIPE
			), act, id
		)

	def smoking(ingredient: Ingredient, result: Item, exp: Float, time: Int, id: Option[String] = None)
	  (act: State[SimpleCookingRecipeBuilder, _]): FoamFurnaceRecipe =
		new FoamFurnaceRecipe(
			SimpleCookingRecipeBuilder.cooking(
				ingredient, result, exp, time, RecipeSerializer.SMOKING_RECIPE
			), act, id
		)
}
