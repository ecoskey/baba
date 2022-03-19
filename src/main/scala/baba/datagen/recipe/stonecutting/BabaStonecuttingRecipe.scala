package baba.datagen.recipe.stonecutting

import baba.datagen.recipe.BabaRecipeBuilderRecipe
import cats.data.State
import net.minecraft.data.recipes.SingleItemRecipeBuilder
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient

class BabaStonecuttingRecipe private(
	internal: SingleItemRecipeBuilder,
	act     : State[SingleItemRecipeBuilder, _],
	id      : Option[String]
) extends BabaRecipeBuilderRecipe(internal, act, id)

object BabaStonecuttingRecipe extends BabaRecipeBuilderRecipe.Ops[SingleItemRecipeBuilder] {
	def apply(ingredient: Ingredient, result: Item, count: Int = 1, id: Option[String] = None)
	  (act: State[SingleItemRecipeBuilder, Unit]): BabaStonecuttingRecipe =
		new BabaStonecuttingRecipe(SingleItemRecipeBuilder.stonecutting(ingredient, result, count), act, id)
}
