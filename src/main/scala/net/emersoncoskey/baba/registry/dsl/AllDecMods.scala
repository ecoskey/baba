package net.emersoncoskey.baba.registry.dsl

import net.emersoncoskey.baba.registry.block.dsl.BlockMods
import net.emersoncoskey.baba.registry.item.dsl.ItemMods

trait AllDecMods {
	object item extends ItemMods

	object block extends BlockMods
}
