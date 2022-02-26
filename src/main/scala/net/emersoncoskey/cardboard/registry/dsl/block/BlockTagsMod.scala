package net.emersoncoskey.cardboard.registry.dsl.block

import net.emersoncoskey.cardboard.CbMod
import net.emersoncoskey.cardboard.datagen.tag.TagAssignment
import net.emersoncoskey.cardboard.datagen.tag.block.CbBlockTagsProvider
import net.emersoncoskey.cardboard.registry.dsl.ModDecMod
import net.minecraft.tags.Tag
import net.minecraft.world.level.block.Block
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent

class BlockTagsMod(tags: Seq[Tag.Named[Block]]) extends ModDecMod[Block] {
	override type E = GatherDataEvent

	override val eventClass: Class[GatherDataEvent] = classOf[GatherDataEvent]

	override def handleEvent(target: Block, event: E, mod: CbMod): Unit = {
		val gen    = event.getGenerator
		val helper = event.getExistingFileHelper
		gen.addProvider(new CbBlockTagsProvider(mod, gen, helper, TagAssignment(target, tags) :: Nil))
	}
}
