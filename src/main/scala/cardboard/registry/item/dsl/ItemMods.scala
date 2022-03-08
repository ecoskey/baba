package cardboard.registry.item.dsl

import cardboard.datagen.recipe.CbRecipe
import cardboard.registry.dsl.ModDecMod
import net.minecraft.tags.Tag
import net.minecraft.world.item.Item

trait ItemMods {
	def recipes(first: Item => CbRecipe, rest: (Item => CbRecipe)*): ModDecMod[Item] = new RecipesMod(first :: rest.toList)

	def tags(first: Tag.Named[Item], rest: Tag.Named[Item]*) = new ItemTagsMod(first :: rest.toList)
}
