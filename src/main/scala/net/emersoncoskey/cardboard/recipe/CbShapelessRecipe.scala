package net.emersoncoskey.cardboard.recipe

import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient

sealed trait CbShapelessRecipe

case class Result(result: Item, count: Int) extends CbShapelessRecipe
case class ~:(ingredient: (Ingredient, Int), rest: CbShapelessRecipe) extends CbShapelessRecipe

object test {
	val test2 =
		(null, 5) ~: (null, 2) ~: Result(null, 5)
}