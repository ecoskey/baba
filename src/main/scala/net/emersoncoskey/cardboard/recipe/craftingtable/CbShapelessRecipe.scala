package net.emersoncoskey.cardboard.recipe.craftingtable

import cats.{Monad, Traverse}
import cats.data.{NonEmptyList, Reader, State}
import cats.implicits.{catsSyntaxFlatMapOps, toTraverseOps}
import net.emersoncoskey.cardboard.CbMod
import net.emersoncoskey.cardboard.recipe.RecipeHaver
import net.minecraft.data.recipes.{FinishedRecipe, ShapelessRecipeBuilder}
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import net.minecraftforge.fml.common.Mod

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

case class CbShapelessRecipe private(
	private val internal: ShapelessRecipeBuilder,
	private val id: Option[String] = None
) {
	private[cardboard] def save(consumer: Consumer[FinishedRecipe], mod: CbMod): Unit = id match {
		case Some(s) => internal.save(consumer, new ResourceLocation(mod.ModId, s))
		case None => internal.save(consumer)
	}
}

object CbShapelessRecipe {
	def ingredients(
		first: (Ingredient, Int),
		rest: (Ingredient,Int)*
	): Reader[ShapelessRecipeBuilder, Unit] = {
		val ingredients = first :: rest.toList
		val singleIngredients = ingredients >>= { case (i, n) => List.fill(n)(i) }
		singleIngredients
		  .traverse(i => Reader { b: ShapelessRecipeBuilder => b.requires(i) } )
		  .map(_ => ())
	}

	def build: Reader[ShapelessRecipeBuilder, CbShapelessRecipe] = Reader(CbShapelessRecipe(_))
	def build(id: String): Reader[ShapelessRecipeBuilder, CbShapelessRecipe] = Reader(CbShapelessRecipe(_, Some(id)))
}