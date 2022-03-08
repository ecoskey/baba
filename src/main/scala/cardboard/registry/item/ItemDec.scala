package cardboard.registry.item

import cardboard.registry.dsl.DecMod
import cardboard.registry.{CbRegistry, Reg, RegistryDec}
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties

class ItemDec[+I <: Item] private(
	val name : String,
	val props: Properties,
	val ctor : Properties => I,
	val mods : Seq[DecMod[Item]]
)(implicit registry: CbRegistry[Item]) extends Reg[Item] {
	def reg: RegistryDec[Item] = new RegistryDec[Item](name, () => ctor(props), mods)
}

object ItemDec {
	def apply(name: String, properties: Properties)(mods: DecMod[Item]*)(implicit registry: CbRegistry[Item]): ItemDec[Item] =
		new ItemDec(name, properties, new Item(_), mods)

	def apply[I <: Item](name: String, ctor: Properties => I, properties: Properties)(mods: DecMod[Item]*)(implicit registry: CbRegistry[Item]): ItemDec[I] =
		new ItemDec(name, properties, ctor, mods)
}
