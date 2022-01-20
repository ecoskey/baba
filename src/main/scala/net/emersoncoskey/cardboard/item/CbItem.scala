package net.emersoncoskey.cardboard.item

import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties

case class CbItem[+I <: Item] private(name: String, item: () => I)

object CbItem {
	def named(name: String): Builder.FirstStep = Builder.FirstStep(name)

	sealed trait Builder[+I <: Item]

	object Builder {
		case class FirstStep private(private val name: String) extends Builder[Item] {
			def custom[I <: Item](ctor: Properties => I): SecondStep[I] = SecondStep(name, ctor)

			def properties(props: Properties): FinalStep[Item] = FinalStep(name, new Item(_), props)
		}

		case class SecondStep[+I <: Item] private(
			private val name: String,
			private val ctor: Properties => I,
		) extends Builder[I] {
			def properties(props: Properties): FinalStep[I] = FinalStep(name, ctor, props)
		}

		case class FinalStep[+I <: Item] private(
			private val name : String,
			private val ctor : Properties => I,
			private val props: Properties
		) extends Builder[I] {
			//todo: recipe providers, item model providers(generated and custom),

			def build: CbItem[I] = CbItem(name, () => ctor(props))
		}
	}
}
