package net.emersoncoskey.cardboard.datagen.decmod

import cats.data.NonEmptyList
import cats.implicits._
import net.emersoncoskey.cardboard.CbMod
import net.emersoncoskey.cardboard.datagen.recipe.{CbRecipe, CbRecipeProvider}
import net.emersoncoskey.cardboard.datagen.tag.TagAssignment
import net.emersoncoskey.cardboard.datagen.tag.item.CbItemTagsProvider
import net.minecraft.client.renderer.RenderType
import net.minecraft.data.{DataGenerator, DataProvider}
import net.minecraft.tags.Tag
import net.minecraft.world.item.Item
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent

import java.util.EventListener

object ItemMods {
	//val renderType: Attr[Item, RenderType, Unit] = rType => DecMod(reg => () => ) //TODO: make rendertype assignment provider

	/*def tags(first: Tag.Named[Item], rest: Tag.Named[Item]*): DecMod[Item, Unit] = DecMod((r, mod) => {
		val assignment: TagAssignment[Item] = TagAssignment(r, first :: rest.toList)
		val listener: EventListener = CbMod.EventListener[GatherDataEvent](e => {
			val generator = e.getGenerator
			val helper = e.getExistingFileHelper
			generator.addProvider(new CbItemTagsProvider())
		})
		(listener.some, ())
	})*/
}