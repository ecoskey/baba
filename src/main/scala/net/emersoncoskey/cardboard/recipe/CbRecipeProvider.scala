package net.emersoncoskey.cardboard.recipe

import net.minecraft.data.DataGenerator
import net.minecraft.data.recipes.{FinishedRecipe, RecipeProvider}

import java.util.function.Consumer

case class CbRecipeProvider(gen: DataGenerator, recipes: RecipeHaver*) extends RecipeProvider(gen) {
	override def buildCraftingRecipes(finishedRecipeConsumer: Consumer[FinishedRecipe]): Unit = {

	}
}
