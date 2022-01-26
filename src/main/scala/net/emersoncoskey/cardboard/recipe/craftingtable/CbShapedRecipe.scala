package net.emersoncoskey.cardboard.recipe.craftingtable

import cats.data.State
import cats.implicits.toTraverseOps
import net.emersoncoskey.cardboard.recipe.CbRecipeBuilderRecipe
import net.minecraft.advancements.CriterionTriggerInstance
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient


class CbShapedRecipe private(
	internal: ShapedRecipeBuilder,
	id      : Option[String] = None,
) extends CbRecipeBuilderRecipe(internal, id)

object CbShapedRecipe {
	case class IngredientKey private(c: Char) extends AnyVal

	object IngredientKey {
		val Empty: IngredientKey = IngredientKey(' ')
	}

	def apply(count: Int = 1, id: Option[String] = None)
	         (act: State[ShapedRecipeBuilder, _])
	         (result: Item): CbShapedRecipe =
		new CbShapedRecipe(act.runS(new ShapedRecipeBuilder(result, count)).value, id)

	def define(c: Char, i: Ingredient): State[ShapedRecipeBuilder, IngredientKey] =
		State(s => (s.define(c, i), IngredientKey(c)))

	def pattern(first: List[IngredientKey], rest: List[IngredientKey]*): State[ShapedRecipeBuilder, Unit] = {
		val rows       = first :: rest.toList
		val charRows   = rows.map(_.map(_.c))
		val stringRows = charRows.map(_.mkString)
		stringRows
		  .traverse(s => State.modify((b: ShapedRecipeBuilder) => b.pattern(s)))
		  .map(_ => ())
	}


	def group(name: String): State[ShapedRecipeBuilder, Unit] = State.modify(_.group(name))

	def unlockedBy(criterionName: String, trigger: CriterionTriggerInstance): State[ShapedRecipeBuilder, Unit] =
		State.modify(_.unlockedBy(criterionName, trigger))
}