package net.emersoncoskey.cardboard.recipe

import net.minecraft.data.recipes.FinishedRecipe

trait RecipeHaver {
	def toFinishedRecipe: FinishedRecipe
}