package foam.registry.item

import foam.registry.RegDec
import foam.registry.dsl.DecMod
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties

import java.util.function.Supplier

class ItemDec[+I <: Item] private(
	val name         : String,
	val props        : Properties,
	val ctor         : Properties => I,
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
