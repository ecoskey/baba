package baba.datagen.tag.item

import baba.BaseMod
import baba.datagen.tag.TagAssignment
import baba.datagen.tag.block.BabaBlockTagsProvider
import net.minecraft.data.DataGenerator
import net.minecraft.data.tags.{BlockTagsProvider, ItemTagsProvider}
import net.minecraft.tags.Tag
import net.minecraft.world.item.Item
import net.minecraftforge.common.data.ExistingFileHelper

class BabaItemTagsProvider(
	mod      : BaseMod,
	gen      : DataGenerator,
	helper   : ExistingFileHelper,
	tagPairs : Seq[TagAssignment[Item]]
) extends ItemTagsProvider(gen, new BabaBlockTagsProvider(mod, gen, helper, Nil), mod.ModId, helper) {
	override def addTags(): Unit = for {
		TagAssignment(i, tags) <- tagPairs
		t <- tags
	} tag(t).add(i)
}
