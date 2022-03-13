package cardboard.registry.item.dsl

import cardboard.CbMod
import cardboard.datagen.tag.TagAssignment
import cardboard.datagen.tag.item.CbItemTagsProvider
import cardboard.registry.dsl.ModDecMod
import net.minecraft.tags.Tag
import net.minecraft.world.item.Item
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent

class ItemTagsMod(tags: Seq[Tag.Named[Item]]) extends ModDecMod[Item] {
	type E = GatherDataEvent

	val eventClass: Class[GatherDataEvent] = classOf[GatherDataEvent]

	def handleEvent(target: Item, event: GatherDataEvent, mod: CbMod[_]): Unit = {
		val gen    = event.getGenerator
		val helper = event.getExistingFileHelper
		gen.addProvider(new CbItemTagsProvider(mod, gen, helper, TagAssignment(target, tags) :: Nil))
	}
}
