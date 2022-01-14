package net.emersoncoskey.cardboard.block

import net.emersoncoskey.cardboard.item.CardboardBlockItem
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour.Properties

import java.util.function.Supplier

case class CardboardBlock[B <: Block, I <: BlockItem] private(name: String, block: Supplier[B], blockItem: Option[CardboardBlockItem[B, I]])

object CardboardBlock {
	def named(name: String): Builder[Block] = Builder.FirstStep(name)

	sealed trait Builder[+B]

	object Builder {
		case class FirstStep private(name: String) extends Builder[Block] {
			def custom[B <: Block](constructor: Properties => B): SecondStep[B] = SecondStep(name, constructor)

			def properties(properties: Properties): FinalStep[Block, BlockItem] = FinalStep(name, new Block(_), properties)
		}

		case class SecondStep[B <: Block] private(name: String, constructor: Properties => B) extends Builder[B] {
			def properties(properties: Properties): FinalStep[B, BlockItem] = FinalStep(name, constructor, properties)
		}

		case class FinalStep[B <: Block, I <: BlockItem] private(name: String, constructor: Properties => B, properties: Properties,
		                                                         blockItem: Option[CardboardBlockItem[B, I]] = None) extends Builder[B] {
			//todo: recipe providers, Block model providers(generated and custom), 

			def build: CardboardBlock[B, I] = CardboardBlock(name, () => constructor(properties), blockItem)
		}
	}
}