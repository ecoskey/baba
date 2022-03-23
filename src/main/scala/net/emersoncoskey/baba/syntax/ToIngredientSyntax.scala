package net.emersoncoskey.baba.syntax

import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient

trait ToIngredientSyntax {
	implicit class FromItem(item: Item) {
		def i: Ingredient = Ingredient.of(item)
	}

	implicit class FromTag(tag: TagKey[Item]) {
		def i: Ingredient = Ingredient.of(tag)
	}
}
