package net.emersoncoskey.baba.datagen.tag.item

import net.emersoncoskey.baba.BaseMod
import net.emersoncoskey.baba.datagen.tag.TagAssignment
import net.emersoncoskey.baba.datagen.tag.block.BabaBlockTagsProvider
import net.minecraft.data.DataGenerator
import net.minecraft.data.tags.ItemTagsProvider
import net.minecraft.world.item.Item
import net.minecraftforge.common.data.ExistingFileHelper

class BabaItemTagsProvider(
	mod     : BaseMod,
	gen     : DataGenerator,
	helper  : ExistingFileHelper,
	tagPairs: Seq[TagAssignment[Item]]
) extends ItemTagsProvider(gen, new BabaBlockTagsProvider(mod, gen, helper, Nil), mod.ModId, helper) {
	override def addTags(): Unit = for {
		TagAssignment(i, tags) <- tagPairs
		t <- tags
	} tag(t).add(i)
}
