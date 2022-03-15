package foam.registry.item.dsl

import foam.BaseMod
import foam.datagen.recipe.{CbRecipe, CbRecipeProvider}
import foam.registry.dsl.ModDecMod
import net.minecraft.world.item.Item
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent

class RecipesMod(recipeFns: Seq[Item => CbRecipe]) extends ModDecMod[Item] {
	type E = GatherDataEvent
	val eventClass: Class[GatherDataEvent] = classOf[GatherDataEvent]

	def handleEvent(target: Item, event: GatherDataEvent, mod: BaseMod): Unit = {
		val gen     = event.getGenerator
		val recipes = recipeFns.map(_(target))
		gen.addProvider(new CbRecipeProvider(mod, gen, recipes))
	}
}
