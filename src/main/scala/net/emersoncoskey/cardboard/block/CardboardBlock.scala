package net.emersoncoskey.cardboard.block

import net.emersoncoskey.cardboard.item.CardboardBlockItem
import net.minecraft.client.renderer.RenderType
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.item.{BlockItem, CreativeModeTab, Item}
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.material.Material
import net.minecraftforge.registries.RegistryObject

import java.util.function.Supplier

case class CardboardBlock[B <: Block, I <: BlockItem] private(
	name      : String,
	block     : Supplier[B],

	blockItem : Option[CardboardBlockItem[B, I]],
	renderType: RenderType,
)

object CardboardBlock {
	def named(name: String): Builder.FirstStep = Builder.FirstStep(name)

	sealed trait Builder[+B <: Block, +I <: BlockItem]

	object Builder {
		case class FirstStep private(private val name: String) extends Builder[Block, BlockItem] {
			def custom[B <: Block](ctor: Properties => B): SecondStep[B] =
				SecondStep(name, ctor)

			def properties(properties: Properties): FinalStep[Block, BlockItem] =
				FinalStep(name, new Block(_), properties)
		}

		case class SecondStep[B <: Block] private(
			private val name: String,
			private val ctor: Properties => B
		) extends Builder[B, BlockItem] {
			def properties(props: Properties): FinalStep[B, BlockItem] =
				FinalStep(name, ctor, props)
		}

		case class FinalStep[B <: Block, I <: BlockItem] private(
			private val name      : String,
			private val ctor      : Properties => B,
			private val props     : Properties,

			private val blockItem : Option[CardboardBlockItem[B, I]] = None,
			private val renderType: RenderType = RenderType.solid
		) extends Builder[B, I] {
			//todo: recipe providers, Block model providers(generated and custom),
			def blockItem(blockItem: CardboardBlockItem[B, I]): FinalStep[B, I] =
				copy(blockItem = Some(blockItem))

			def renderType(renderType: RenderType): FinalStep[B, I] =
				copy(renderType = renderType)

			def build: CardboardBlock[B, I] =
				CardboardBlock(name, () => ctor(props), blockItem, renderType)
		}
	}
}