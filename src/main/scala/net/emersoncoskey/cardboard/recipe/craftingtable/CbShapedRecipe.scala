package net.emersoncoskey.cardboard.recipe.craftingtable

import cats.data.State
import net.emersoncoskey.cardboard.CbMod
import net.emersoncoskey.cardboard.recipe.{CbRecipe, CbRecipeBuilderRecipe}
import net.minecraft.advancements.CriterionTriggerInstance
import net.minecraft.data.recipes.{FinishedRecipe, ShapedRecipeBuilder}
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient

import java.util.function.Consumer


class CbShapedRecipe private(
	internal: ShapedRecipeBuilder,
	id      : Option[String] = None,
) extends CbRecipeBuilderRecipe(internal, id)

object CbShapedRecipe {
	case class IngredientKey private(private val c: Char) extends AnyVal
	object IngredientKey { val Empty: IngredientKey = IngredientKey('_') }

	def apply(
		act: State[ShapedRecipeBuilder, _],
		id: Option[String] = None,
		count: Int
	)(result: Item): CbShapedRecipe =
		new CbShapedRecipe(act.runS(new ShapedRecipeBuilder(result, count)).value, id)

	def define(c: Char, i: Ingredient): State[ShapedRecipeBuilder, IngredientKey] =
		State(s => (s.define(c, i), IngredientKey(c)))

	def pattern(): State[ShapedRecipeBuilder, Unit] = ???

	def group(name: String): State[ShapedRecipeBuilder, Unit] = State.modify(_.group(name))

	def unlockedBy(criterionName: String, trigger: CriterionTriggerInstance): State[ShapedRecipeBuilder, Unit] =
		State.modify(_.unlockedBy(criterionName, trigger))
}