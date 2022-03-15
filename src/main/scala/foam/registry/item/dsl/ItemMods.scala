package foam.registry.item.dsl

import foam.datagen.recipe.FoamRecipe
import foam.registry.dsl.ModDecMod
import net.minecraft.tags.Tag
import net.minecraft.world.item.Item

trait ItemMods {
	def recipes(first: Item => FoamRecipe, rest: (Item => FoamRecipe)*): ModDecMod[Item] = new RecipesMod(first :: rest.toList)

	def tags(first: Tag.Named[Item], rest: Tag.Named[Item]*) = new ItemTagsMod(first :: rest.toList)
}
