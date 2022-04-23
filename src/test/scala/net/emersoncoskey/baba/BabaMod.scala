package net.emersoncoskey.baba

import net.emersoncoskey.baba.block.TestBlock
import net.emersoncoskey.baba.registry.{block, blockItem}
import net.minecraft.world.item.{CreativeModeTab, Item}
import net.minecraftforge.fml.common.Mod
import cats.syntax.flatMap._

/** An example implementation of BaseMod, and the entrypoint for this mod. */
@Mod(BabaMod.ModId)
object BabaMod {
	final val ModId = "baba"

	val baba: Baba = Baba(ModId,
		block("amongus_block", new TestBlock(_))
		  >>= blockItem("amongus", new Item.Properties().tab(CreativeModeTab.TAB_FOOD)),
		block("cheeseblock")
		  >>= blockItem("cheese", new Item.Properties().tab(CreativeModeTab.TAB_FOOD))
	)
}
