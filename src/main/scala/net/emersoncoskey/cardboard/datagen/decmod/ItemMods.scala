package net.emersoncoskey.cardboard.datagen.decmod

import net.emersoncoskey.cardboard.CbMod
import net.emersoncoskey.cardboard.datagen.recipe.{CbRecipe, CbRecipeProvider}
import net.minecraft.data.{DataGenerator, DataProvider}
import net.minecraft.world.item.Item

object ItemMods {
	case class Recipes(first: Item => CbRecipe, rest: (Item => CbRecipe)*) extends DecMod[Item] {
		/** Ex: "item model", "tags", "block states", etc. */
		override val name: String = "recipes"
		override def apply(mod: CbMod, gen: DataGenerator, i: Item): DataProvider = CbRecipeProvider(mod, gen, (first :: rest.toList).map(_(i)))
	}
}
