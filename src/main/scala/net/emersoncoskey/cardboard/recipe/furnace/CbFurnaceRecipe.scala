package net.emersoncoskey.cardboard.recipe.furnace

import cats.data.State
import net.emersoncoskey.cardboard.recipe.{CbRecipe, CbRecipeBuilderRecipe}
import net.emersoncoskey.cardboard.recipe.craftingtable.CbShapelessRecipe.unlockedBy
import net.minecraft.advancements.CriterionTriggerInstance
import net.minecraft.advancements.critereon.{EnterBlockTrigger, EntityPredicate, ItemPredicate, StatePropertiesPredicate}
import net.minecraft.data.recipes.{ShapelessRecipeBuilder, SimpleCookingRecipeBuilder}
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.{Ingredient, RecipeSerializer}
import net.minecraft.world.level.block.Block

class CbFurnaceRecipe private(
	internal: SimpleCookingRecipeBuilder,
	id      : Option[String] = None,
) extends CbRecipeBuilderRecipe[SimpleCookingRecipeBuilder](internal, id)

object CbFurnaceRecipe extends CbRecipeBuilderRecipe.Ops[SimpleCookingRecipeBuilder] {
	def campfire(ingredient: Ingredient, result: Item, exp: Float, time: Int, id: Option[String] = None)
	            (act: State[SimpleCookingRecipeBuilder, _]): CbFurnaceRecipe = {
		new CbFurnaceRecipe(
			act.runS(SimpleCookingRecipeBuilder.cooking(
				ingredient, result, exp, time, RecipeSerializer.CAMPFIRE_COOKING_RECIPE
			)).value,
			id
		)
	}

	def blasting(ingredient: Ingredient, result: Item, exp: Float, time: Int, id: Option[String] = None)
	            (act: State[SimpleCookingRecipeBuilder, _]): CbFurnaceRecipe =
		new CbFurnaceRecipe(
			act.runS(SimpleCookingRecipeBuilder.cooking(
				ingredient, result, exp, time, RecipeSerializer.BLASTING_RECIPE
			)).value,
			id
		)

	def smelting(ingredient: Ingredient, result: Item, exp: Float, time: Int, id: Option[String] = None)
	            (act: State[SimpleCookingRecipeBuilder, _]): CbFurnaceRecipe =
		new CbFurnaceRecipe(
			act.runS(SimpleCookingRecipeBuilder.cooking(
				ingredient, result, exp, time, RecipeSerializer.SMELTING_RECIPE
			)).value,
			id
		)

	def smoking(ingredient: Ingredient, result: Item, exp: Float, time: Int, id: Option[String] = None)
		       (act: State[SimpleCookingRecipeBuilder, _]): CbFurnaceRecipe =
		new CbFurnaceRecipe(
			act.runS(SimpleCookingRecipeBuilder.cooking(
				ingredient, result, exp, time, RecipeSerializer.SMOKING_RECIPE
			)).value,
			id
		)
}
