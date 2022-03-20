package baba.registry.dsl

import baba.registry.block.dsl.BlockMods
import baba.registry.item.dsl.ItemMods

trait AllDecMods {
	object item extends ItemMods
	object block extends BlockMods
}
