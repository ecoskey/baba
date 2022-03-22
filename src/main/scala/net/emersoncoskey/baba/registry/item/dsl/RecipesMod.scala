package net.emersoncoskey.baba.registry.item.dsl

import net.emersoncoskey.baba.BaseMod
import net.emersoncoskey.baba.datagen.recipe.{BabaRecipe, BabaRecipeProvider}
import net.emersoncoskey.baba.registry.dsl.ModDecMod
import net.minecraft.world.item.Item
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent

class RecipesMod(recipeFns: Seq[Item => BabaRecipe]) extends ModDecMod[Item] {
	type E = GatherDataEvent
	val eventClass: Class[GatherDataEvent] = classOf[GatherDataEvent]

	def handleEvent(target: Item, event: GatherDataEvent, mod: BaseMod): Unit = {
		val gen     = event.getGenerator
		val recipes = recipeFns.map(_(target))
		gen.addProvider(new BabaRecipeProvider(mod, gen, recipes))
	}
}
