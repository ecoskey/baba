package net.emersoncoskey.cardboard

import net.emersoncoskey.cardboard.block.CardboardBlock
import net.emersoncoskey.cardboard.item.CardboardItem
import net.minecraft.world.item.{BlockItem, CreativeModeTab, Item}
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material

object TestModule extends CardboardModule(Cardboard) {
	lazy val Items : Seq[CardboardItem[Item]]   = Seq(Amongus)
	lazy val Blocks: Seq[CardboardBlock[Block]] = Seq(AmongusBlock)

	lazy val AmongusBlock: CardboardBlock[TestBlock] =
		CardboardBlock.named("amongus2_block")
		              .custom(new TestBlock(_))
		              .properties(BlockBehaviour.Properties.of(Material.STONE))
		              .build

	lazy val Amongus     : CardboardItem[BlockItem]  =
		CardboardItem.named("amongus2")
		             .custom(new BlockItem(TestModule(AmongusBlock).get, _))
		             .properties(new Item.Properties().tab(CreativeModeTab.TAB_MISC))
		             .build

}
