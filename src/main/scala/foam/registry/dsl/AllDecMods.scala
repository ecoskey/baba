package foam.registry.dsl

import foam.registry.block.dsl.BlockMods
import foam.registry.item.dsl.ItemMods

trait AllDecMods {
	object item extends ItemMods
	object block extends BlockMods
}
