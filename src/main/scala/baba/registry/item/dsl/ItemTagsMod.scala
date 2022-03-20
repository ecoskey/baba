package baba.registry.item.dsl

import baba.BaseMod
import baba.datagen.tag.TagAssignment
import baba.datagen.tag.item.BabaItemTagsProvider
import baba.registry.dsl.ModDecMod
import net.minecraft.tags.{Tag, TagKey}
import net.minecraft.world.item.Item
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent

class ItemTagsMod(tags: Seq[TagKey[Item]]) extends ModDecMod[Item] {
	type E = GatherDataEvent

	val eventClass: Class[GatherDataEvent] = classOf[GatherDataEvent]

	def handleEvent(target: Item, event: GatherDataEvent, mod: BaseMod): Unit = {
		val gen    = event.getGenerator
		val helper = event.getExistingFileHelper
		gen.addProvider(new BabaItemTagsProvider(mod, gen, helper, TagAssignment(target, tags) :: Nil))
	}
}
