package net.emersoncoskey.cardboard.recipe.stonecutting

import cats.data.State
import net.emersoncoskey.cardboard.recipe.CbRecipeBuilderRecipe
import net.minecraft.data.recipes.SingleItemRecipeBuilder
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient

class CbStonecuttingRecipe private(
	internal: SingleItemRecipeBuilder,
	act: State[SingleItemRecipeBuilder, _],
	id: Option[String]
) extends CbRecipeBuilderRecipe(internal, act, id)

object CbStonecuttingRecipe extends CbRecipeBuilderRecipe.Ops[SingleItemRecipeBuilder] {
	def apply(ingredient: Ingredient, result: Item, count: Int = 1, id: Option[String] = None)
	         (act: State[SingleItemRecipeBuilder, Unit]): CbStonecuttingRecipe =
		new CbStonecuttingRecipe(SingleItemRecipeBuilder.stonecutting(ingredient, result, count), act, id)
}
