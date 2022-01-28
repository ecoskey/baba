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

object CbFurnaceRecipe {
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

	def group(name: String): State[SimpleCookingRecipeBuilder, Unit] = State.modify(_.group(name))

	def unlockedBy(criterionName: String, trigger: CriterionTriggerInstance): State[SimpleCookingRecipeBuilder, Unit] =
		State.modify(_.unlockedBy(criterionName, trigger))

	/* [UTILITY METHODS] **********************************************************************************************/

	def unlockedByItem(item: Item): State[SimpleCookingRecipeBuilder, Unit] =
		unlockedBy(
			s"has_${ item.getRegistryName.getPath }",
			CbRecipe.inventoryTrigger(ItemPredicate.Builder.item.of(item).build)
		)

	def unlockedByInBlock(block: Block): State[SimpleCookingRecipeBuilder, Unit] =
		unlockedBy(
			s"inside_of_${ block.getRegistryName.getPath }",
			new EnterBlockTrigger.TriggerInstance(
				EntityPredicate.Composite.ANY,
				block, StatePropertiesPredicate.ANY
			)
		)
}
