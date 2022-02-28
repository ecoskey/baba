package cardboard.syntax

import net.minecraft.tags.Tag
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient

trait ToIngredientSyntax {
	implicit class FromItem(item: Item) {
		def i: Ingredient = Ingredient.of(item)
	}

	implicit class FromTag(tag: Tag[Item]) {
		def i: Ingredient = Ingredient.of(tag)
	}
}
