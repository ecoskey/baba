package net.emersoncoskey.baba.modules

import net.emersoncoskey.baba.block.TestBlock
import net.emersoncoskey.baba.data.DNil
import net.emersoncoskey.baba.dsl.decmods._
import net.emersoncoskey.baba.registry.block.BlockDec
import net.emersoncoskey.baba.registry.item.ItemDec
import net.emersoncoskey.baba.registry.painting.PaintingDec
import net.emersoncoskey.baba.syntax.all._
import net.emersoncoskey.baba.{Baba, TestCreativeTab}
import net.minecraft.world.item.{BlockItem, Item}
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraftforge.common.Tags

object TestModule {
	val AmongusBlock: BlockDec[Block] = BlockDec("amongus_block", new TestBlock(_), BlockBehaviour.Properties.of(Material.STONE))(
		block.tags(Tags.Blocks.ORES_DIAMOND, Tags.Blocks.BARRELS, Tags.Blocks.GLASS, Tags.Blocks.COBBLESTONE)
	)

	val Amongus: ItemDec[BlockItem] = ItemDec("amongus", new BlockItem(Baba(AmongusBlock), _), new Item.Properties().tab(TestCreativeTab))(
		item.tags(Tags.Items.ORES_DIAMOND)
	)

	val BabaPainting: PaintingDec = PaintingDec("baba", 16, 16)

	// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	val Module: Baba.Module = Baba.Module(Seq(Amongus) %: Seq(AmongusBlock) %: Seq(BabaPainting) %: DNil)
}
