package cardboard.registry.block.dsl

import cardboard.CbMod
import cardboard.datagen.tag.TagAssignment
import cardboard.datagen.tag.block.CbBlockTagsProvider
import cardboard.registry.dsl.ModDecMod
import net.minecraft.tags.Tag
import net.minecraft.world.level.block.Block
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent

class BlockTagsMod(tags: Seq[Tag.Named[Block]]) extends ModDecMod[Block] {
	type E = GatherDataEvent

	val eventClass: Class[GatherDataEvent] = classOf[GatherDataEvent]

	def handleEvent(target: Block, event: GatherDataEvent, mod: CbMod): Unit = {
		val gen    = event.getGenerator
		val helper = event.getExistingFileHelper
		gen.addProvider(new CbBlockTagsProvider(mod, gen, helper, TagAssignment(target, tags) :: Nil))
	}
}
