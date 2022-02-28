package cardboard.registry

import cardboard.registry.block.dsl.BlockMods
import cardboard.registry.item.dsl.ItemMods

package object dsl {
	val I: ItemMods.type  = ItemMods
	val B: BlockMods.type = BlockMods
}
