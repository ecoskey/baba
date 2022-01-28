package net.emersoncoskey.cardboard.item

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.level.block.Block


case class CbBlockItem[-B <: Block, +I <: BlockItem] private(name: String, item: B => I)

object CbBlockItem {
	def named[B <: Block](name: String): Builder.FirstStep[B] = Builder.FirstStep(name)

	sealed trait Builder[-B <: Block, +I <: BlockItem]

	object Builder {
		case class FirstStep[B <: Block] private(private val name: String) extends Builder[B, BlockItem] {
			def custom[I <: BlockItem](ctor: (Block, Properties) => I): SecondStep[B, I] = SecondStep(name, ctor)

			def properties(props: Properties): FinalStep[B, BlockItem] = FinalStep(name, new BlockItem(_, _), props)
		}

		case class SecondStep[B <: Block, I <: BlockItem] private(
			private val name: String,
			private val ctor: (B, Properties) => I
		) extends Builder[B, I] {
			def properties(props: Properties): FinalStep[B, I] = FinalStep(name, ctor, props)
		}

		case class FinalStep[B <: Block, I <: BlockItem] private(
			private val name : String,
			private val ctor : (B, Properties) => I,
			private val props: Properties
		) extends Builder[B, I] {
			//todo: recipe providers, item model providers(generated and custom),

			def build: CbBlockItem[B, I] = CbBlockItem(name, ctor(_, props))
		}
	}
}
