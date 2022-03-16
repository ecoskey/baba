package foam.datagen.recipe.craftingtable

import foam.datagen.recipe.FoamRecipeBuilderRecipe
import cats.data.State
import cats.implicits.{toFunctorOps, toTraverseOps}
import net.minecraft.data.recipes.ShapedRecipeBuilder
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import foam.syntax.all._


class FoamShapedRecipe private(
	internal: ShapedRecipeBuilder,
	act     : State[ShapedRecipeBuilder, _],
	id      : Option[String] = None,
) extends FoamRecipeBuilderRecipe(internal, act, id)

object FoamShapedRecipe extends FoamRecipeBuilderRecipe.Ops[ShapedRecipeBuilder] {
	case class IngredientKey private(c: Char) extends AnyVal

	object IngredientKey {
		val Empty: IngredientKey = IngredientKey(' ')
	}

	def apply(result: Item, count: Int = 1, id: Option[String] = None)
	  (act: State[ShapedRecipeBuilder, _]): FoamShapedRecipe =
		new FoamShapedRecipe(new ShapedRecipeBuilder(result, count), act, id)

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

	/* ["SHORTCUT" RECIPE METHODS] ************************************************************************************/

	def packing2x2(ingredient: Item, id: Option[String] = None, groupName: Option[String] = None)(result: Item): FoamShapedRecipe = {
		val actualId = id.getOrElse(s"${ result.getRegistryName.getPath }_from_packing2x2_${ ingredient.getRegistryName.getPath }")
		FoamShapedRecipe(result, 1, Some(actualId))(for {
			x <- define('#', ingredient.i)
			row = List(x, x)
			_ <- pattern(row, row)
			_ <- unlockedByItem(ingredient)
			_ <- group(groupName.orNull) //accursed
		} yield ())
	}

	def packing3x3(ingredient: Item, id: Option[String] = None, groupName: Option[String] = None)(result: Item): FoamShapedRecipe = {
		val actualId = id.getOrElse(s"${ result.getRegistryName.getPath }_from_packing3x3_${ ingredient.getRegistryName.getPath }")
		FoamShapedRecipe(result, 1, Some(actualId))(for {
			x <- define('#', ingredient.i)
			row = List(x, x, x)
			_ <- pattern(row, row, row)
			_ <- unlockedByItem(ingredient)
			_ <- group(groupName.orNull) //accursed
		} yield ())
	}
}