package net.emersoncoskey.baba.registry.item.dsl

import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item

trait ItemMods {
	def tags(first: TagKey[Item], rest: TagKey[Item]*) = new ItemTagsMod(first :: rest.toList)
}
