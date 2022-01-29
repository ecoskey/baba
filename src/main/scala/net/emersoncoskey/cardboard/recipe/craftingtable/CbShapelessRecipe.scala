package net.emersoncoskey.cardboard.recipe.craftingtable

import cats.data.State
import cats.implicits.{toFunctorOps, toTraverseOps}
import net.emersoncoskey.cardboard.Syntax.ItemOps
import net.emersoncoskey.cardboard.recipe.{CbRecipe, CbRecipeBuilderRecipe}
import net.minecraft.advancements.CriterionTriggerInstance
import net.minecraft.advancements.critereon.{EnterBlockTrigger, EntityPredicate, ItemPredicate, StatePropertiesPredicate}
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.block.Block

class CbShapelessRecipe private(
	internal: ShapelessRecipeBuilder,
	id      : Option[String] = None,
) extends CbRecipeBuilderRecipe(internal, id)

object CbShapelessRecipe extends CbRecipeBuilderRecipe.Ops[ShapelessRecipeBuilder] {
	def apply(result: Item, count: Int = 1, id: Option[String] = None)
	  (act: State[ShapelessRecipeBuilder, _]): CbShapelessRecipe =
		new CbShapelessRecipe(act.runS(new ShapelessRecipeBuilder(result, count)).value, id)

	def ingredients(
		first: (Ingredient, Int),
		rest : (Ingredient, Int)*
	): State[ShapelessRecipeBuilder, Unit] =
		(first :: rest.toList)
		  .traverse { case (i, n) => State.modify[ShapelessRecipeBuilder](_.requires(i, n)) }
		  .void

	/* ["SHORTCUT" RECIPE METHODS] ************************************************************************************/

	def conversion(ingredient: Item, id: Option[String] = None, groupName: Option[String] = None)
	              (result: Item): CbShapelessRecipe = {
		val actualId = id.getOrElse(s"${result.getRegistryName.getPath}_from_${result.getRegistryName.getPath}")
		CbShapelessRecipe(result, 1, Some(actualId))(for {
			_ <- ingredients(ingredient.i -> 1)
			_ <- unlockedByItem(ingredient)
			_ <- group(groupName.orNull)
		} yield ())
	}
}