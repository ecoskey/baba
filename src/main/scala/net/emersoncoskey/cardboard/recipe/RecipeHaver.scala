package net.emersoncoskey.cardboard.recipe

import net.minecraft.data.recipes.FinishedRecipe
import net.minecraftforge.fml.common.Mod

import java.util.function.Consumer

trait CbRecipe {
	def save(consumer: Consumer[FinishedRecipe], mod: Mod)
}