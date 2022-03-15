package foam.registry.item.dsl

import foam.BaseMod
import foam.datagen.tag.TagAssignment
import foam.datagen.tag.item.FoamItemTagsProvider
import foam.registry.dsl.ModDecMod
import net.minecraft.tags.Tag
import net.minecraft.world.item.Item
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent

class ItemTagsMod(tags: Seq[Tag.Named[Item]]) extends ModDecMod[Item] {
	type E = GatherDataEvent

	val eventClass: Class[GatherDataEvent] = classOf[GatherDataEvent]

	def handleEvent(target: Item, event: GatherDataEvent, mod: BaseMod): Unit = {
		val gen    = event.getGenerator
		val helper = event.getExistingFileHelper
		gen.addProvider(new FoamItemTagsProvider(mod, gen, helper, TagAssignment(target, tags) :: Nil))
	}
}
