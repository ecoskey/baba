package cardboard.registry.item

import cardboard.registry.RegDec
import cardboard.registry.dsl.DecMod
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties

import java.util.function.Supplier

class ItemDec[+I <: Item] private(
	val name         : String,
	val props        : Properties,
	val ctor         : Properties => I,
	override val mods: Seq[DecMod[Item]]
) extends RegDec[Item] {
	lazy val sup: Supplier[Item] = () => ctor(props)
}

object ItemDec {
	def apply(name: String, properties: Properties)(mods: DecMod[Item]*): ItemDec[Item] =
		new ItemDec(name, properties, new Item(_), mods)

	def apply[I <: Item](name: String, ctor: Properties => I, properties: Properties)(mods: DecMod[Item]*): ItemDec[I] =
		new ItemDec(name, properties, ctor, mods)
}
