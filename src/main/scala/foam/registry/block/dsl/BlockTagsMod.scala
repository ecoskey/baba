package foam.registry.block.dsl

import foam.BaseMod
import foam.datagen.tag.TagAssignment
import foam.datagen.tag.block.FoamBlockTagsProvider
import foam.registry.dsl.ModDecMod
import net.minecraft.tags.{Tag, TagKey}
import net.minecraft.world.level.block.Block
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent

class BlockTagsMod(tags: Seq[TagKey[Block]]) extends ModDecMod[Block] {
	type E = GatherDataEvent

	val eventClass: Class[GatherDataEvent] = classOf[GatherDataEvent]

	def handleEvent(target: Block, event: GatherDataEvent, mod: BaseMod): Unit = {
		val gen    = event.getGenerator
		val helper = event.getExistingFileHelper
		gen.addProvider(new FoamBlockTagsProvider(mod, gen, helper, TagAssignment(target, tags) :: Nil))
	}
}
