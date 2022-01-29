package net.emersoncoskey.cardboard.recipe.stonecutting

import cats.data.State
import net.emersoncoskey.cardboard.recipe.CbRecipeBuilderRecipe
import net.minecraft.data.recipes.SingleItemRecipeBuilder
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient

class CbStonecuttingRecipe private(
	internal: SingleItemRecipeBuilder,
	id: Option[String]
) extends CbRecipeBuilderRecipe(internal, id)

object CbStonecuttingRecipe extends CbRecipeBuilderRecipe.Ops[SingleItemRecipeBuilder] {
	def apply(ingredient: Ingredient, result: Item, count: Int = 1, id: Option[String] = None)
	         (act: State[SingleItemRecipeBuilder, Unit]): CbStonecuttingRecipe =
		new CbStonecuttingRecipe(act.runS(SingleItemRecipeBuilder.stonecutting(ingredient, result, count)).value, id)
}
