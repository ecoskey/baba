package net.emersoncoskey.baba.datagen.recipe.craftingtable

import cats.data.State
import cats.implicits.{toFunctorOps, toTraverseOps}
import net.emersoncoskey.baba.datagen.recipe.BabaRecipeBuilderRecipe
import net.emersoncoskey.baba.datagen.recipe.BabaRecipeBuilderRecipe.Ops
import net.emersoncoskey.baba.syntax.ingredient._
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient

class BabaShapelessRecipe private(
	internal: ShapelessRecipeBuilder,
	act     : State[ShapelessRecipeBuilder, _],
	id      : Option[String] = None,
) extends BabaRecipeBuilderRecipe(internal, act, id)

object BabaShapelessRecipe extends Ops[ShapelessRecipeBuilder] {
	def apply(result: Item, count: Int = 1, id: Option[String] = None)
	  (act: State[ShapelessRecipeBuilder, _]): BabaShapelessRecipe =
		new BabaShapelessRecipe(new ShapelessRecipeBuilder(result, count), act, id)

	def ingredients(
		first: (Ingredient, Int),
		rest : (Ingredient, Int)*
	): State[ShapelessRecipeBuilder, Unit] =
		(first :: rest.toList)
		  .traverse { case (i, n) => State.modify[ShapelessRecipeBuilder](_.requires(i, n)) }
		  .void

	/* ["SHORTCUT" RECIPE METHODS] ************************************************************************************/

	def conversion(ingredient: Item, id: Option[String] = None, groupName: Option[String] = None)
	  (result: Item): BabaShapelessRecipe = {
		val actualId = id.getOrElse(s"${ result.getRegistryName.getPath }_from_${ ingredient.getRegistryName.getPath }")
		BabaShapelessRecipe(result, 1, Some(actualId))(for {
			_ <- ingredients(ingredient.i -> 1)
			_ <- unlockedByItem(ingredient)
			_ <- group(groupName.orNull)
		} yield ())
	}
}