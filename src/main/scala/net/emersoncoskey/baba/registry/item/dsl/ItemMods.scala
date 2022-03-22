package net.emersoncoskey.baba.registry.item.dsl

import net.emersoncoskey.baba.datagen.recipe.BabaRecipe
import net.emersoncoskey.baba.registry.dsl.ModDecMod
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item

trait ItemMods {
	def recipes(first: Item => BabaRecipe, rest: (Item => BabaRecipe)*): ModDecMod[Item] = new RecipesMod(first :: rest.toList)

	def tags(first: TagKey[Item], rest: TagKey[Item]*) = new ItemTagsMod(first :: rest.toList)
}
