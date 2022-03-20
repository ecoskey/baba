package baba.registry.item

import baba.registry.RegDec
import baba.registry.dsl.DecMod
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties

import java.util.function.Supplier

/** Used to declare an [[Item]] to be added to the game */
class ItemDec[+I <: Item] private(
	val name: String,
	props: Properties,
	ctor: Properties => I,
	override val modifiers: Seq[DecMod[Item]]
) extends RegDec[Item] {
	lazy val supplier: Supplier[Item] = () => ctor(props)
}

object ItemDec {
	def apply(name: String, properties: Properties)(mods: DecMod[Item]*): ItemDec[Item] =
		new ItemDec(name, properties, new Item(_), mods)

	def apply[I <: Item](name: String, ctor: Properties => I, properties: Properties)(mods: DecMod[Item]*): ItemDec[I] =
		new ItemDec(name, properties, ctor, mods)
}
