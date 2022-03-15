package foam.datagen.recipe

import cardboard.CbMod
import net.minecraft.data.DataGenerator
import net.minecraft.data.recipes.{FinishedRecipe, RecipeProvider}

import java.util.function.Consumer

class FoamRecipeProvider(mod: CbMod, gen: DataGenerator, recipes: Seq[FoamRecipe]) extends RecipeProvider(gen) {
	override def buildCraftingRecipes(finishedRecipeConsumer: Consumer[FinishedRecipe]): Unit =
		recipes.foreach(_.save(finishedRecipeConsumer, mod))
}
