package net.emersoncoskey.cardboard.registry.dsl.item

import net.emersoncoskey.cardboard.CbMod
import net.emersoncoskey.cardboard.datagen.recipe.{CbRecipe, CbRecipeProvider}
import net.emersoncoskey.cardboard.registry.dsl.ModDecMod
import net.minecraft.world.item.Item
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent

class RecipesMod(recipeFns: Seq[Item => CbRecipe]) extends ModDecMod[Item] {
	type E = GatherDataEvent
	val eventClass: Class[GatherDataEvent] = classOf[GatherDataEvent]

	def handleEvent(target: Item, event: GatherDataEvent, mod: CbMod): Unit = {
		val gen     = event.getGenerator
		val recipes = recipeFns.map(_(target))
		gen.addProvider(new CbRecipeProvider(mod, gen, recipes))
	}
}
