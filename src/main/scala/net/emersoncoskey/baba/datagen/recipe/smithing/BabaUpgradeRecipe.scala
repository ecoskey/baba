package net.emersoncoskey.baba.datagen.recipe.smithing

import cats.data.State
import net.emersoncoskey.baba.BaseMod
import net.emersoncoskey.baba.datagen.recipe.BabaRecipe
import net.minecraft.data.recipes.{FinishedRecipe, UpgradeRecipeBuilder}
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient
import net.emersoncoskey.baba.syntax.ingredient._

import java.util.function.Consumer

class BabaUpgradeRecipe private(
	internal: UpgradeRecipeBuilder,
	act     : State[UpgradeRecipeBuilder, _],
	id      : String,
) extends BabaRecipe {
	private[baba] override def save(consumer: Consumer[FinishedRecipe], mod: BaseMod): Unit =
		act.runS(internal).value.save(consumer, new ResourceLocation(mod.ModId, id))
}

object BabaUpgradeRecipe {
	def apply(base: Ingredient, addition: Ingredient, result: Item, id: String)
	  (act: State[UpgradeRecipeBuilder, Unit]): BabaUpgradeRecipe =
		new BabaUpgradeRecipe(UpgradeRecipeBuilder.smithing(base, addition, result), act, id)

	def apply(base: Item, addition: Item, result: Item, id: Option[String] = None)
	  (act: State[UpgradeRecipeBuilder, Unit]): BabaUpgradeRecipe =
		new BabaUpgradeRecipe(
			UpgradeRecipeBuilder.smithing(base.i, addition.i, result),
			act,
			id.getOrElse(s"upgrade_${ base.getRegistryName.getPath }_with_${ addition.getRegistryName.getPath }")
		)
}