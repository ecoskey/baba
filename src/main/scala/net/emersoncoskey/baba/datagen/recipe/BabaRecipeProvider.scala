package net.emersoncoskey.baba.datagen.recipe

import net.emersoncoskey.baba.BaseMod
import net.minecraft.data.DataGenerator
import net.minecraft.data.recipes.{FinishedRecipe, RecipeProvider}

import java.util.function.Consumer

class BabaRecipeProvider(mod: BaseMod, gen: DataGenerator, recipes: Seq[BabaRecipe]) extends RecipeProvider(gen) {
	override def buildCraftingRecipes(finishedRecipeConsumer: Consumer[FinishedRecipe]): Unit =
		recipes.foreach(_.save(finishedRecipeConsumer, mod))
}
