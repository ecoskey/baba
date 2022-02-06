package net.emersoncoskey.cardboard.datagen.tag.block

import net.emersoncoskey.cardboard.CbMod
import net.minecraft.data.DataGenerator
import net.minecraft.data.tags.BlockTagsProvider
import net.minecraft.tags.Tag
import net.minecraft.world.level.block.Block
import net.minecraftforge.common.data.ExistingFileHelper

case class CbBlockTagsProvider(
	mod     : CbMod,
	gen     : DataGenerator,
	helper  : ExistingFileHelper,
	tagPairs: Seq[(Block, Seq[Tag.Named[Block]])]
) extends BlockTagsProvider(gen, mod.ModId, helper) {
	override def addTags(): Unit = for {
		(b, tags) <- tagPairs
		t <- tags
	} tag(t).add(b)
}
