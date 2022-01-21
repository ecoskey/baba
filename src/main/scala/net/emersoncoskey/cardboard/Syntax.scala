package net.emersoncoskey.cardboard

import net.minecraft.tags.Tag
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient

object Syntax {
	implicit class ItemOps(item: Item) {
		def i: Ingredient = Ingredient.of(item)
	}
	implicit class TagOps(tag: Tag[Item]) {
		def i: Ingredient = Ingredient.of(tag)
	}
}
