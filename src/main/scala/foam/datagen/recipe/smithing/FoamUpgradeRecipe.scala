package foam.datagen.recipe.smithing

import foam.datagen.recipe.FoamRecipe
import cats.data.State
import foam.BaseMod
import net.minecraft.data.recipes.{FinishedRecipe, UpgradeRecipeBuilder}
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import foam.syntax.ingredient._


import java.util.function.Consumer

class FoamUpgradeRecipe private(
	internal: UpgradeRecipeBuilder,
	act     : State[UpgradeRecipeBuilder, _],
	id      : String,
) extends FoamRecipe {
	private[cardboard] override def save(consumer: Consumer[FinishedRecipe], mod: BaseMod): Unit =
		act.runS(internal).value.save(consumer, new ResourceLocation(mod.ModId, id))
}

object FoamUpgradeRecipe {
	def apply(base: Ingredient, addition: Ingredient, result: Item, id: String)
	  (act: State[UpgradeRecipeBuilder, Unit]): FoamUpgradeRecipe =
		new FoamUpgradeRecipe(UpgradeRecipeBuilder.smithing(base, addition, result), act, id)

	def apply(base: Item, addition: Item, result: Item, id: Option[String] = None)
	  (act: State[UpgradeRecipeBuilder, Unit]): FoamUpgradeRecipe =
		new FoamUpgradeRecipe(
			UpgradeRecipeBuilder.smithing(base.i, addition.i, result),
			act,
			id.getOrElse(s"upgrade_${ base.getRegistryName.getPath }_with_${ addition.getRegistryName.getPath }")
		)
}