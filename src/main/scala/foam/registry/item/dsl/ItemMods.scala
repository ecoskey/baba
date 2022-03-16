package foam.registry.item.dsl

import foam.datagen.recipe.FoamRecipe
import foam.registry.dsl.ModDecMod
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item

trait ItemMods {
	def recipes(first: Item => FoamRecipe, rest: (Item => FoamRecipe)*): ModDecMod[Item] = new RecipesMod(first :: rest.toList)

	def tags(first: TagKey[Item], rest: TagKey[Item]*) = new ItemTagsMod(first :: rest.toList)
}
