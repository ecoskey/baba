package cardboard.datagen.recipe.smithing

import cardboard.CbMod
import cardboard.datagen.recipe.CbRecipe
import cats.data.State
import net.minecraft.data.recipes.{FinishedRecipe, UpgradeRecipeBuilder}
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import cardboard.syntax.all._

import java.util.function.Consumer

class CbUpgradeRecipe private(
	internal: UpgradeRecipeBuilder,
	act     : State[UpgradeRecipeBuilder, _],
	id      : String,
) extends CbRecipe {
	private[cardboard] override def save(consumer: Consumer[FinishedRecipe], mod: CbMod[_]): Unit =
		act.runS(internal).value.save(consumer, new ResourceLocation(mod.ModId, id))
}

object CbUpgradeRecipe {
	def apply(base: Ingredient, addition: Ingredient, result: Item, id: String)
	  (act: State[UpgradeRecipeBuilder, Unit]): CbUpgradeRecipe =
		new CbUpgradeRecipe(UpgradeRecipeBuilder.smithing(base, addition, result), act, id)

	def apply(base: Item, addition: Item, result: Item, id: Option[String] = None)
	  (act: State[UpgradeRecipeBuilder, Unit]): CbUpgradeRecipe =
		new CbUpgradeRecipe(
			UpgradeRecipeBuilder.smithing(base.i, addition.i, result),
			act,
			id.getOrElse(s"upgrade_${ base.getRegistryName.getPath }_with_${ addition.getRegistryName.getPath }")
		)
}