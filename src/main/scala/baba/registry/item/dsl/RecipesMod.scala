package baba.registry.item.dsl

import baba.BaseMod
import baba.datagen.recipe.{BabaRecipe, BabaRecipeProvider}
import baba.registry.dsl.ModDecMod
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
