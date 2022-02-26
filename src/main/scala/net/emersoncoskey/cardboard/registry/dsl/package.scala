package net.emersoncoskey.cardboard.registry

import net.emersoncoskey.cardboard.registry.dsl.block.BlockMods
import net.emersoncoskey.cardboard.registry.dsl.item.ItemMods

package object dsl {
	val I: ItemMods.type  = ItemMods
	val B: BlockMods.type = BlockMods
}
