package net.emersoncoskey.cardboard.block

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour.Properties

import java.util.function.Supplier

case class CardboardBlock[B <: Block] private (name: String, block: Supplier[B])

object CardboardBlock {
	sealed trait Builder[+B]
	object Builder {
		case class FirstStep(name: String) extends Builder[Block] {
			def custom[B <: Block](constructor: Properties => B): SecondStep[B] = SecondStep(name, constructor)
			def properties(properties: Properties): RestStep[Block] = RestStep(name, new Block(_), properties)
		}

		case class SecondStep[B <: Block](name: String, constructor: Properties => B) extends Builder[B] {
			def properties(properties: Properties): RestStep[B] = RestStep(name, constructor, properties)
		}

		case class RestStep[B <: Block](name: String, constructor: Properties => B, properties: Properties) extends Builder[B] {
			//todo: recipe providers, Block model providers(generated and custom), 

			def build: CardboardBlock[B] = CardboardBlock(name, () => constructor(properties))
		}
	}
}