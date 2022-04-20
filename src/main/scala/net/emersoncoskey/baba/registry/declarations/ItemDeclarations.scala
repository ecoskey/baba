package net.emersoncoskey.baba.registry.declarations

import net.emersoncoskey.baba.registry.{DecMod, Register, declareWithMods}
import net.minecraft.world.item.{BlockItem, Item}
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.level.block.Block
import net.minecraftforge.registries.RegistryObject

trait ItemDeclarations {
	def item(name: String, mods: DecMod[Item, Item]*): Register[RegistryObject[Item]] =
		item(name, new Item(_), mods:_*)

	def item(name: String, props: Properties, mods: DecMod[Item, Item]*): Register[RegistryObject[Item]] =
		item(name, new Item(_), props, mods:_*)

	def item[I <: Item](name: String, ctor: Properties => I, mods: DecMod[Item, I]*): Register[RegistryObject[I]] =
		item(name, ctor, new Properties, mods:_*)

	def item[I <: Item](name: String, ctor: Properties => I, props: Properties, mods: DecMod[Item, I]*): Register[RegistryObject[I]] =
		declareWithMods(name, ctor(props), mods.toList)

	// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	def blockItem[B <: Block](name: String, mods: DecMod[Item, BlockItem]*)(block: RegistryObject[B]):
	  Register[(RegistryObject[B], RegistryObject[BlockItem])] = blockItem[B](name, new Properties, mods:_*)(block)

	def blockItem[B <: Block](name: String, props: Properties, mods: DecMod[Item, BlockItem]*)(block: RegistryObject[B]):
	  Register[(RegistryObject[B], RegistryObject[BlockItem])] = item(name, new BlockItem(block.get, _), props, mods:_*).map((block, _))
}
