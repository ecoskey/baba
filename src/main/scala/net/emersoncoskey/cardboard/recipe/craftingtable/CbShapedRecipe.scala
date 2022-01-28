package net.emersoncoskey.cardboard.recipe.craftingtable

import cats.data.State
import cats.implicits.{toFunctorOps, toTraverseOps}
import net.emersoncoskey.cardboard.Syntax.ItemOps
import net.emersoncoskey.cardboard.recipe.{CbRecipe, CbRecipeBuilderRecipe}
import net.minecraft.advancements.CriterionTriggerInstance
import net.minecraft.advancements.critereon.{EnterBlockTrigger, EntityPredicate, ItemPredicate, StatePropertiesPredicate}
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.block.Block


class CbShapedRecipe private(
	val internal: ShapedRecipeBuilder,
	val id      : Option[String] = None,
) extends CbRecipeBuilderRecipe(internal, id)

object CbShapedRecipe {
	case class IngredientKey private(c: Char) extends AnyVal

	object IngredientKey {
		val Empty: IngredientKey = IngredientKey(' ')
	}

	def apply(result: Item, count: Int = 1, id: Option[String] = None)
	  (act: State[ShapedRecipeBuilder, _]): CbShapedRecipe =
		new CbShapedRecipe(act.runS(new ShapedRecipeBuilder(result, count)).value, id)

	def define(c: Char, i: Ingredient): State[ShapedRecipeBuilder, IngredientKey] =
		State(s => (s.define(c, i), IngredientKey(c)))

	def pattern(first: List[IngredientKey], rest: List[IngredientKey]*): State[ShapedRecipeBuilder, Unit] = {
		val rows       = first :: rest.toList
		val charRows   = rows.map(_.map(_.c))
		val stringRows = charRows.map(_.mkString)
		stringRows
		  .traverse(s => State.modify((b: ShapedRecipeBuilder) => b.pattern(s)))
		  .void
	}

	def group(name: String): State[ShapedRecipeBuilder, Unit] = State.modify(_.group(name))

	def unlockedBy(criterionName: String, trigger: CriterionTriggerInstance): State[ShapedRecipeBuilder, Unit] =
		State.modify(_.unlockedBy(criterionName, trigger))


	/* [UTILITY METHODS] **********************************************************************************************/

	def unlockedByItem(item: Item): State[ShapedRecipeBuilder, Unit] =
		unlockedBy(
			s"has_${ item.getRegistryName.getPath }",
			CbRecipe.inventoryTrigger(ItemPredicate.Builder.item.of(item).build)
		)

	def unlockedByInBlock(block: Block): State[ShapedRecipeBuilder, Unit] =
		unlockedBy(
			s"inside_of_${ block.getRegistryName.getPath }",
			new EnterBlockTrigger.TriggerInstance(
				EntityPredicate.Composite.ANY,
				block, StatePropertiesPredicate.ANY
			)
		)

	/* ["SHORTCUT" RECIPE METHODS] ************************************************************************************/

	def packing2x2(ingredient: Item, id: Option[String] = None, groupName: Option[String] = None)
	              (result: Item): CbShapedRecipe = {
		val actualId = id.getOrElse(
			s"${result.getRegistryName.getPath}_from_packing2x2_${ingredient.getRegistryName.getPath}"
		)
		CbShapedRecipe(result, 1, Some(actualId))(for {
			x <- define('#', ingredient.i)
			row = List(x, x)
			_ <- pattern(row, row)
			_ <- unlockedByItem(ingredient)
			_ <- group(groupName.orNull) //accursed
		} yield ())
	}

	def packing3x3(ingredient: Item, id: Option[String] = None, groupName: Option[String] = None)
	              (result: Item): CbShapedRecipe = {
		val actualId = id.getOrElse(
			s"${result.getRegistryName.getPath}_from_packing3x3_${ingredient.getRegistryName.getPath}"
		)
		CbShapedRecipe(result, 1, Some(actualId))(for {
			x <- define('#', ingredient.i)
			row = List(x, x, x)
			_ <- pattern(row, row, row)
			_ <- unlockedByItem(ingredient)
			_ <- group(groupName.orNull) //accursed
		} yield ())
	}
}