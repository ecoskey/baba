package net.emersoncoskey.cardboard.registry

import net.emersoncoskey.cardboard.registry.block.dsl.BlockMods
import net.emersoncoskey.cardboard.registry.item.dsl.ItemMods

package object dsl {
	val I: ItemMods.type  = ItemMods
	val B: BlockMods.type = BlockMods
}
