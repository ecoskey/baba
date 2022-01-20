package net.emersoncoskey.cardboard.block

import net.emersoncoskey.cardboard.item.{CbBlockItem, CbItem}
import net.minecraft.client.renderer.RenderType
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour.Properties

case class CbBlock[+B <: Block/*, +I <: BlockItem*/] private(
	name      : String,
	block     : () => B,

	/*blockItem : Option[B => CardboardItem[I]],*/
	renderType: RenderType,
)

object CbBlock {
	def named(name: String): Builder.FirstStep = Builder.FirstStep(name)

	sealed trait Builder[+B <: Block/*, +I <: BlockItem*/]

	object Builder {
		case class FirstStep private(private val name: String) extends Builder[Block/*, BlockItem*/] {
			def custom[B <: Block](ctor: Properties => B): SecondStep[B] =
				SecondStep(name, ctor)

			def properties(properties: Properties): FinalStep[Block/*, BlockItem*/] =
				FinalStep(name, new Block(_), properties)
		}

		case class SecondStep[+B <: Block] private(
			private val name: String,
			private val ctor: Properties => B
		) extends Builder[B/*, BlockItem*/] {
			def properties(props: Properties): FinalStep[B/*, BlockItem*/] =
				FinalStep(name, ctor, props)
		}

		case class FinalStep[+B <: Block/*, +I <: BlockItem*/] private(
			private val name : String,
			private val ctor : Properties => B,
			private val props: Properties,

			/*private val blockItem : Option[B => CardboardItem[I]] = None,*/
			private val renderType: RenderType = RenderType.solid
		) extends Builder[B/*, I*/] {
			//todo: recipe providers, Block model providers(generated and custom),
			/*def blockItem[U >: I](blockItem: B => CardboardItem[U]): FinalStep[B, U] =
				copy(blockItem = Some(blockItem))*/

			def renderType(renderType: RenderType): FinalStep[B/*, I*/] =
				copy(renderType = renderType)

			def build: CbBlock[B/*, I*/] =
				CbBlock(name, () => ctor(props), /*blockItem, */ renderType)
		}
	}
}