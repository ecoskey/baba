package net.emersoncoskey.baba

import cats.syntax.flatMap._
import net.emersoncoskey.baba.block.TestBlock
import net.emersoncoskey.baba.registry.{block, blockItem, lang, tags, _}
import net.minecraft.world.item.{CreativeModeTab, Item}
import net.minecraft.world.level.block.Block
import net.minecraftforge.common.Tags

object TestModule {
	val module: McAction[Unit] = for {
		amongus <- block[TestBlock]("amongus_block", new TestBlock(_)) >>=
		  blockItem("amongus", new Item.Properties().tab(CreativeModeTab.TAB_FOOD))

		cheese <- block("cheeseblock", tags(Tags.Blocks.ORES)) >>=
		  blockItem("cheese", new Item.Properties().tab(CreativeModeTab.TAB_FOOD))
	} yield ()
}
