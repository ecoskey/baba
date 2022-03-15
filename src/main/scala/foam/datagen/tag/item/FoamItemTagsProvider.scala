package foam.datagen.tag.item

import foam.BaseMod
import foam.datagen.tag.TagAssignment
import foam.datagen.tag.block.FoamBlockTagsProvider
import net.minecraft.data.DataGenerator
import net.minecraft.data.tags.{BlockTagsProvider, ItemTagsProvider}
import net.minecraft.tags.Tag
import net.minecraft.world.item.Item
import net.minecraftforge.common.data.ExistingFileHelper

class FoamItemTagsProvider(
	mod      : BaseMod,
	gen      : DataGenerator,
	helper   : ExistingFileHelper,
	tagPairs : Seq[TagAssignment[Item]]
) extends ItemTagsProvider(gen, new FoamBlockTagsProvider(mod, gen, helper, Nil), mod.ModId, helper) {
	override def addTags(): Unit = for {
		TagAssignment(i, tags) <- tagPairs
		t <- tags
	} tag(t).add(i)
}
