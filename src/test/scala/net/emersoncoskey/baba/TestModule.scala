package net.emersoncoskey.baba

import cats.syntax.flatMap._
import net.emersoncoskey.baba.block.TestBlock
import net.emersoncoskey.baba.registry._
import net.minecraft.world.item.{CreativeModeTab, Item}
import net.minecraftforge.common.Tags

object TestModule {
	val module: Register[Unit] = for {
		_ <- block("amongus_block", new TestBlock(_)) >>=
		        blockItem("amongus", new Item.Properties().tab(CreativeModeTab.TAB_FOOD))
		_ <- block("cheeseblock", tags(Tags.Blocks.ORES)) >>=
		        blockItem("cheese", new Item.Properties().tab(CreativeModeTab.TAB_FOOD))
	} yield ()
}
