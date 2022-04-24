package net.emersoncoskey.baba.registry.mods

import net.emersoncoskey.baba.datagen.tag.{BabaTagsProvider, TagPair}
import net.emersoncoskey.baba.registry._
import net.minecraft.tags.TagKey
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent
import net.minecraftforge.registries.IForgeRegistryEntry

trait MiscMods {
	def tags[R <: IForgeRegistryEntry[R]: Registrable, A <: R](tags: TagKey[R]*): SimpleDecMod[R] = target =>
		EventHandler.Mod[GatherDataEvent](classOf[GatherDataEvent], e => {
			val gen = e.getGenerator
			val helper = e.getExistingFileHelper
			val mod = e.getModContainer
			gen.addProvider(new BabaTagsProvider[R](mod.getModId, gen, helper, TagPair[R](target.get, tags.toList) :: Nil))
		})
}
