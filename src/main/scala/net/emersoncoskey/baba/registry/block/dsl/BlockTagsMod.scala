package net.emersoncoskey.baba.registry.block.dsl

import net.emersoncoskey.baba.BaseMod
import net.emersoncoskey.baba.datagen.tag.TagAssignment
import net.emersoncoskey.baba.datagen.tag.block.BabaBlockTagsProvider
import net.emersoncoskey.baba.registry.DSL.ModDecMod
import net.minecraft.tags.{Tag, TagKey}
import net.minecraft.world.level.block.Block
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent

class BlockTagsMod(tags: Seq[TagKey[Block]]) extends ModDecMod[Block] {
	type E = GatherDataEvent

	val eventClass: Class[GatherDataEvent] = classOf[GatherDataEvent]

	def handleEvent(target: Block, event: GatherDataEvent, mod: BaseMod): Unit = {
		val gen    = event.getGenerator
		val helper = event.getExistingFileHelper
		gen.addProvider(new BabaBlockTagsProvider(mod, gen, helper, TagAssignment(target, tags) :: Nil))
	}
}
