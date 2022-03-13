package cardboard.datagen.tag.item

import cardboard.CbMod
import cardboard.datagen.tag.TagAssignment
import cardboard.datagen.tag.block.CbBlockTagsProvider
import net.minecraft.data.DataGenerator
import net.minecraft.data.tags.{BlockTagsProvider, ItemTagsProvider}
import net.minecraft.tags.Tag
import net.minecraft.world.item.Item
import net.minecraftforge.common.data.ExistingFileHelper

class CbItemTagsProvider(
	mod      : CbMod[_],
	gen      : DataGenerator,
	helper   : ExistingFileHelper,
	tagPairs : Seq[TagAssignment[Item]]
) extends ItemTagsProvider(gen, new CbBlockTagsProvider(mod, gen, helper, Nil), mod.ModId, helper) {
	override def addTags(): Unit = for {
		TagAssignment(i, tags) <- tagPairs
		t <- tags
	} tag(t).add(i)
}
