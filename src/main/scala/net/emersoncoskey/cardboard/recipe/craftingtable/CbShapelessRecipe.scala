package net.emersoncoskey.cardboard.recipe.craftingtable

import cats.data.{Reader, State}
import cats.implicits.toTraverseOps
import net.emersoncoskey.cardboard.CbMod
import net.emersoncoskey.cardboard.recipe.{CbRecipe, CbRecipeBuilderRecipe}
import net.minecraft.advancements.CriterionTriggerInstance
import net.minecraft.data.recipes.{FinishedRecipe, ShapelessRecipeBuilder}
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient

import java.util.function.Consumer

//import scala.language.implicitConversions

/*
case class CbShapelessRecipe private(
	name       : String,
	group      : String,
	ingredients: NonEmptyList[(Ingredient, Int)],
	result     : Item,
	count      : Int
) extends RecipeHaver {
	override def toFinishedRecipe: FinishedRecipe = ???
}


object CbShapelessRecipe {
	//implicit def toTuple2[A](a: A): (A, Int) = (a, 1)
	def named(name: String): Builder.FirstStep = Builder.FirstStep(name)

	sealed trait Builder

	object Builder {
		case class FirstStep private(private val name: String) extends Builder {
			def ingredients(first: (Ingredient, Int), rest: (Ingredient, Int)*): FinalStep =
				Builder.FinalStep(name, NonEmptyList.of(first, rest: _*))
		}

		case class FinalStep private(
			private val name       : String,
			private val ingredients: NonEmptyList[(Ingredient, Int)],
			private val group      : String = ""
		) extends Builder {
			def group(group: String): FinalStep = copy(group = group)

			def result(count: Int)(item: Item): CbShapelessRecipe = CbShapelessRecipe(name, ingredients, item, count)
		}
	}
}*/

class CbShapelessRecipe private(
	internal: ShapelessRecipeBuilder,
	id      : Option[String] = None,
) extends CbRecipeBuilderRecipe(internal, id)

object CbShapelessRecipe {
	def apply(
		act: State[ShapelessRecipeBuilder, _],
		id: Option[String] = None,
		count: Int
	)(result: Item): CbShapelessRecipe =
		new CbShapelessRecipe(act.runS(new ShapelessRecipeBuilder(result, count)).value, id)

	def ingredients(
		first: (Ingredient, Int),
		rest : (Ingredient, Int)*
	): State[ShapelessRecipeBuilder, Unit] =
		(first :: rest.toList)
		  .flatMap { case (i, n) => List.fill(n)(i) }
		  .traverse(i => State.modify[ShapelessRecipeBuilder](_.requires(i)))
		  .map(_ => ())

	def group(name: String): State[ShapelessRecipeBuilder, Unit] = State.modify(_.group(name))
	def unlockedBy(criterionName: String, trigger: CriterionTriggerInstance): State[ShapelessRecipeBuilder, Unit] =
		State.modify(_.unlockedBy(criterionName, trigger))
}