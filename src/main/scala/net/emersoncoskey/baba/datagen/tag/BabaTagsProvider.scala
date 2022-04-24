package net.emersoncoskey.baba.datagen.tag

import net.emersoncoskey.baba.registry.Registrable
import net.minecraft.data.DataGenerator
import net.minecraft.data.tags.TagsProvider
import net.minecraftforge.common.data.ExistingFileHelper
import net.minecraftforge.registries.IForgeRegistryEntry

class BabaTagsProvider[R <: IForgeRegistryEntry[R]: Registrable](
	modId : String,
	gen   : DataGenerator,
	helper: ExistingFileHelper,
	tags  : List[TagPair[R]]
) extends TagsProvider[R](gen, Registrable[R].getVanillaRegistry, modId, helper) {
	def getName: String = "Baba Tags Provider"

	def addTags(): Unit = for {
		pair <- tags
		t <- pair.tags
	} yield tag(t).add(pair.target)
}
