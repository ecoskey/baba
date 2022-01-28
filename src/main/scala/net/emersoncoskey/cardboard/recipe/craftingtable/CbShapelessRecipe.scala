package net.emersoncoskey.cardboard.recipe.craftingtable

import cats.data.State
import cats.implicits.{toFunctorOps, toTraverseOps}
import net.emersoncoskey.cardboard.Syntax.ItemOps
import net.emersoncoskey.cardboard.recipe.CbRecipeBuilderRecipe
import net.minecraft.advancements.CriterionTriggerInstance
import net.minecraft.advancements.critereon.{EnterBlockTrigger, EntityPredicate, InventoryChangeTrigger, ItemPredicate, MinMaxBounds}
import net.minecraft.core.Registry
import net.minecraft.data.recipes.ShapelessRecipeBuilder
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.block.Block

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
	def apply(result: Item, count: Int = 1, id: Option[String] = None)
	         (act: State[ShapelessRecipeBuilder, _]): CbShapelessRecipe =
		new CbShapelessRecipe(act.runS(new ShapelessRecipeBuilder(result, count)).value, id)

	def ingredients(
		first: (Ingredient, Int),
		rest : (Ingredient, Int)*
	): State[ShapelessRecipeBuilder, Unit] =
		(first :: rest.toList)
		  .traverse{ case (i, n) => State.modify[ShapelessRecipeBuilder](_.requires(i, n)) }
		  .void

	def unlockedBy(criterionName: String, trigger: CriterionTriggerInstance): State[ShapelessRecipeBuilder, Unit] =
		State.modify(_.unlockedBy(criterionName, trigger))

	def group(name: String): State[ShapelessRecipeBuilder, Unit] = State.modify(_.group(name))

	/* [UTILITY METHODS] **********************************************************************************************/

	def itemPredicateTrigger(
		firstPred: ItemPredicate,
		restPred: ItemPredicate*
	): InventoryChangeTrigger.TriggerInstance =
		new InventoryChangeTrigger.TriggerInstance(
			EntityPredicate.Composite.ANY,
			MinMaxBounds.Ints.ANY,
			MinMaxBounds.Ints.ANY,
			MinMaxBounds.Ints.ANY,
			(firstPred :: restPred.toList).toArray
		)

	private def itemName(item: Item): ResourceLocation = Registry.ITEM.getKey(item)

	def unlockedByItem(item: Item): State[ShapelessRecipeBuilder, Unit] =
		unlockedBy(s"has_${itemName(item).getPath}", itemPredicateTrigger(item.i))

	def unlockedByInsideBlock(name: String, block: Block): State[ShapelessRecipeBuilder, Unit] =
		unlockedBy(name, EnterBlockTrigger.TriggerInstance.entersBlock(block))

	def conversion(initial: Ingredient, result: Item, id: Option[String] = None): CbShapelessRecipe =
		CbShapelessRecipe(result, 1, id)(for {
			_ <- ingredients(initial -> 1)
			_ <- unlockedBy()
		} yield ())
}