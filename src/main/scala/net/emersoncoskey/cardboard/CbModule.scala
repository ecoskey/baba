package net.emersoncoskey.cardboard

import net.emersoncoskey.cardboard.registry.block.CbBlock
import net.emersoncoskey.cardboard.registry.item.CbItem
import net.emersoncoskey.cardboard.registry.potion.CbPotion
import net.minecraft.world.item.Item
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.level.block.Block

trait CbModule {
	val items : Seq[CbItem[Item]]
	val blocks: Seq[CbBlock[Block]]
	val potions: Seq[CbPotion[Potion]]
}