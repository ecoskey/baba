package net.emersoncoskey.baba.registry.mods

import net.emersoncoskey.baba.datagen.lang.BabaLangProvider
import net.emersoncoskey.baba.datagen.tag.{BabaTagsProvider, TagPair}
import net.emersoncoskey.baba.registry._
import net.emersoncoskey.baba.util.Lang
import net.minecraft.tags.TagKey
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent
import net.minecraftforge.registries.IForgeRegistryEntry
import net.minecraft.Util

trait MiscMods {
	def tags[R <: IForgeRegistryEntry[R]: Registrable](tags: TagKey[R]*): SimpleDecMod[R] = target =>
		EventHandler.Mod[GatherDataEvent](classOf[GatherDataEvent], e => {
			val gen = e.getGenerator
			val helper = e.getExistingFileHelper
			val mod = e.getModContainer
			gen.addProvider(new BabaTagsProvider[R](mod.getModId, gen, helper, TagPair[R](target.get, tags.toList) :: Nil))
		})

	def lang[R <: IForgeRegistryEntry[R]: Translatable](name: String): SimpleDecMod[R] = lang(Lang.en_us -> name)

	def lang[R <: IForgeRegistryEntry[R]: Translatable](mappings: (Lang, String)*): SimpleDecMod[R] = target =>
		EventHandler.Mod[GatherDataEvent](classOf[GatherDataEvent], e => {
			val gen = e.getGenerator
			val mod = e.getModContainer
			val descriptionId = Util.makeDescriptionId(Translatable[R].tpe, target.getId)
			val actualMappings = mappings.toList.map {
				case (lang, name) => lang -> List(descriptionId -> name)
			}
			actualMappings.foreach(println)
			gen.addProvider(new BabaLangProvider(mod.getModId, gen, actualMappings))
		})
}
