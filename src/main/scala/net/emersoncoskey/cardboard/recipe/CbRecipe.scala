package net.emersoncoskey.cardboard.recipe

import net.emersoncoskey.cardboard.CbMod
import net.minecraft.data.recipes.FinishedRecipe
import net.minecraftforge.fml.common.Mod

import java.util.function.Consumer

trait CbRecipe {
	private[cardboard] def save(consumer: Consumer[FinishedRecipe], mod: CbMod): Unit

}