package net.emersoncoskey.cardboard.tag.item

import net.emersoncoskey.cardboard.CbMod
import net.minecraft.data.DataGenerator
import net.minecraft.data.tags.{BlockTagsProvider, ItemTagsProvider}
import net.minecraft.tags.Tag
import net.minecraft.world.item.Item
import net.minecraftforge.common.data.ExistingFileHelper

case class CbItemTagsProvider(
	mod      : CbMod,
	gen      : DataGenerator,
	blockTags: BlockTagsProvider,
	helper   : ExistingFileHelper,
	tagPairs : Seq[(Item, Seq[Tag.Named[Item]])]
) extends ItemTagsProvider(gen, blockTags, mod.ModId, helper) {
	override def addTags(): Unit = for {
		(i, tags) <- tagPairs
		t <- tags
	} tag(t).add(i)
}
