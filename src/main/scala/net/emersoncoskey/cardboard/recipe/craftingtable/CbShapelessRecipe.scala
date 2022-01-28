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
	val internal: ShapelessRecipeBuilder,
	val id      : Option[String] = None,
) extends CbRecipeBuilderRecipe(internal, id)

object CbShapelessRecipe {
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

	def unlockedBy(criterionName: String, trigger: CriterionTriggerInstance): State[ShapelessRecipeBuilder, Unit] =
		State.modify(_.unlockedBy(criterionName, trigger))

	def group(name: String): State[ShapelessRecipeBuilder, Unit] = State.modify(_.group(name))

	/* [UTILITY METHODS] **********************************************************************************************/

	def unlockedByItem(item: Item): State[ShapelessRecipeBuilder, Unit] =
		unlockedBy(
			s"has_${ item.getRegistryName.getPath }",
			CbRecipe.inventoryTrigger(ItemPredicate.Builder.item.of(item).build)
		)

	def unlockedByInBlock(block: Block): State[ShapelessRecipeBuilder, Unit] =
		unlockedBy(
			s"inside_of_${block.getRegistryName.getPath}",
			new EnterBlockTrigger.TriggerInstance(
				EntityPredicate.Composite.ANY,
				block, StatePropertiesPredicate.ANY
			)
		)

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