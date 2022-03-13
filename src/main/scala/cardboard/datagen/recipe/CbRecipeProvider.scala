package cardboard.datagen.recipe

import cardboard.CbMod
import net.minecraft.data.DataGenerator
import net.minecraft.data.recipes.{FinishedRecipe, RecipeProvider}

import java.util.function.Consumer

class CbRecipeProvider(mod: CbMod[_], gen: DataGenerator, recipes: Seq[CbRecipe]) extends RecipeProvider(gen) {
	override def buildCraftingRecipes(finishedRecipeConsumer: Consumer[FinishedRecipe]): Unit =
		recipes.foreach(_.save(finishedRecipeConsumer, mod))
}
