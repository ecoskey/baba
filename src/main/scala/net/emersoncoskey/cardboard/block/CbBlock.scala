package net.emersoncoskey.cardboard.block

import net.emersoncoskey.cardboard.model.CbModel
import net.minecraft.client.renderer.RenderType
import net.minecraft.tags.Tag
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraftforge.client.model.generators.BlockModelBuilder

case class CbBlock[+B <: Block] private(
	name      : String,
	block     : () => B,

	renderType: RenderType,
	tags: List[Tag.Named[Block]],
	model: CbModel[BlockModelBuilder]
)

object CbBlock {
	def named(name: String): Builder.FirstStep = Builder.FirstStep(name)

	sealed trait Builder[+B <: Block]

	object Builder {
		case class FirstStep private(private val name: String) extends Builder[Block] {
			def custom[B <: Block](ctor: Properties => B): SecondStep[B] =
				SecondStep(name, ctor)

			def properties(properties: Properties): FinalStep[Block] =
				FinalStep(name, new Block(_), properties)
		}

		case class SecondStep[+B <: Block] private(
			private val name: String,
			private val ctor: Properties => B
		) extends Builder[B] {
			def properties(props: Properties): FinalStep[B] =
				FinalStep(name, ctor, props)
		}

		case class FinalStep[+B <: Block] private(
			private val name      : String,
			private val ctor      : Properties => B,
			private val props     : Properties,

			private val renderType: RenderType = RenderType.solid,
			private val tags: List[Tag.Named[Block]] = Nil,
		) extends Builder[B] {
			//todo: recipe providers, Block model providers(generated and custom),

			def renderType(renderType: RenderType): FinalStep[B] =
				copy(renderType = renderType)

			def tags(first: Tag.Named[Block], rest: Tag.Named[Block]*): FinalStep[B] =
				copy(tags = first :: rest.toList ::: tags)

			def build: CbBlock[B] =
				CbBlock(name, () => ctor(props), renderType, tags)
		}
	}
}