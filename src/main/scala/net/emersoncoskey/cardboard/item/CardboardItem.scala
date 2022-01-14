package net.emersoncoskey.cardboard.item

import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties

import java.util.function.Supplier

case class CardboardItem[I <: Item] private (name: String, item: Supplier[I])

object CardboardItem {
	def withName(name: String): Builder[Item] = Builder.FirstStep(name)

	sealed trait Builder[+I <: Item]

	object Builder {
		case class FirstStep(name: String) extends Builder[Item] {
			def custom[I <: Item](constructor: Properties => I): SecondStep[I] = SecondStep(name, constructor)

			def properties(properties: Properties): RestStep[Item] = RestStep(name, new Item(_), properties)
		}

		case class SecondStep[I <: Item](name: String, constructor: Properties => I) extends Builder[I] {
			def properties(properties: Properties): RestStep[I] = RestStep(name, constructor, properties)
		}

		case class RestStep[I <: Item](name: String, constructor: Properties => I, properties: Properties) extends Builder[I] {
			//todo: recipe providers, item model providers(generated and custom),

			def build: CardboardItem[I] = CardboardItem(name, () => constructor(properties))
		}
	}
}
