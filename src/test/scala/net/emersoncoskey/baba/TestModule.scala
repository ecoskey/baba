package net.emersoncoskey.baba

import cats.syntax.flatMap._
import net.emersoncoskey.baba.block.TestBlock
import net.emersoncoskey.baba.registry.{Register, block, blockItem}
import net.minecraft.world.item.{CreativeModeTab, Item}

object TestModule {
	val module: Register[Unit] = for {
		amongus <- block("amongus_block", new TestBlock(_)) >>=
		        blockItem("amongus", new Item.Properties().tab(CreativeModeTab.TAB_FOOD))
		(amongusBlock, amongusItem) = amongus
		_ <- block("cheeseblock") >>=
		        blockItem("cheese", new Item.Properties().tab(CreativeModeTab.TAB_FOOD))
	} yield ()
}
