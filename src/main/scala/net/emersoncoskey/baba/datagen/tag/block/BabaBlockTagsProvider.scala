package net.emersoncoskey.baba.datagen.tag.block

import net.emersoncoskey.baba.BaseMod
import net.emersoncoskey.baba.datagen.tag.TagAssignment
import net.minecraft.data.DataGenerator
import net.minecraft.data.tags.BlockTagsProvider
import net.minecraft.world.level.block.Block
import net.minecraftforge.common.data.ExistingFileHelper

class BabaBlockTagsProvider(
	mod     : BaseMod,
	gen     : DataGenerator,
	helper  : ExistingFileHelper,
	assignments: Seq[TagAssignment[Block]]
) extends BlockTagsProvider(gen, mod.ModId, helper) {
	override def addTags(): Unit = for {
		TagAssignment(b, tags) <- assignments
		t <- tags
	} tag(t).add(b)
}
