package net.emersoncoskey.cardboard.registry.dsl.item

import net.emersoncoskey.cardboard.CbMod
import net.emersoncoskey.cardboard.datagen.recipe.{CbRecipe, CbRecipeProvider}
import net.emersoncoskey.cardboard.registry.dsl.ModDecMod
import net.minecraft.world.item.Item
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent

object ItemMods {

	def recipes(first: Item => CbRecipe, rest: (Item => CbRecipe)*): ModDecMod[Item] = new RecipesMod(first :: rest.toList)
}
