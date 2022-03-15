package foam.datagen.recipe.stonecutting

import foam.datagen.recipe.FoamRecipeBuilderRecipe
import cats.data.State
import net.minecraft.data.recipes.SingleItemRecipeBuilder
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient

class FoamStonecuttingRecipe private(
	internal: SingleItemRecipeBuilder,
	act     : State[SingleItemRecipeBuilder, _],
	id      : Option[String]
) extends FoamRecipeBuilderRecipe(internal, act, id)

object FoamStonecuttingRecipe extends FoamRecipeBuilderRecipe.Ops[SingleItemRecipeBuilder] {
	def apply(ingredient: Ingredient, result: Item, count: Int = 1, id: Option[String] = None)
	  (act: State[SingleItemRecipeBuilder, Unit]): FoamStonecuttingRecipe =
		new FoamStonecuttingRecipe(SingleItemRecipeBuilder.stonecutting(ingredient, result, count), act, id)
}
