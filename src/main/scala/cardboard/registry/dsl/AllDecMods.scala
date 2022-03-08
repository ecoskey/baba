package cardboard.registry.dsl

import cardboard.registry.block.dsl.BlockMods
import cardboard.registry.item.dsl.ItemMods

trait AllDecMods {
	object I extends ItemMods
	object B extends BlockMods
}
