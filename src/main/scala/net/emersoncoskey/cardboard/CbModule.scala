package net.emersoncoskey.cardboard

import net.emersoncoskey.cardboard.block.CbBlock
import net.emersoncoskey.cardboard.item.CbItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block

trait CbModule {
	val items : Seq[CbItem[Item]]
	val blocks: Seq[CbBlock[Block]]
}