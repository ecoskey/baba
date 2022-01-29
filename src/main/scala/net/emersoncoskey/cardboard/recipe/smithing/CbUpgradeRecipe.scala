package net.emersoncoskey.cardboard.recipe.smithing

import cats.data.State
import net.emersoncoskey.cardboard.CbMod
import net.emersoncoskey.cardboard.Syntax.ItemOps
import net.emersoncoskey.cardboard.recipe.CbRecipe
import net.minecraft.data.recipes.{FinishedRecipe, UpgradeRecipeBuilder}
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.crafting.Ingredient

import java.util.function.Consumer

class CbUpgradeRecipe private(
	internal: UpgradeRecipeBuilder,
	id: String,
) extends CbRecipe {
	private[cardboard] override def save(consumer: Consumer[FinishedRecipe], mod: CbMod): Unit =
		internal.save(consumer, new ResourceLocation(mod.ModId, id))
}

object CbUpgradeRecipe {
	def apply(base: Ingredient, addition: Ingredient, result: Item, id: String)
	         (act: State[UpgradeRecipeBuilder, Unit]): CbUpgradeRecipe =
		new CbUpgradeRecipe(
			act.runS(UpgradeRecipeBuilder.smithing(base, addition, result)).value,
			id
		)

	def apply(base: Item, addition: Item, result: Item, id: Option[String] = None)
	  (act: State[UpgradeRecipeBuilder, Unit]): CbUpgradeRecipe =
		new CbUpgradeRecipe(
			act.runS(UpgradeRecipeBuilder.smithing(base.i, addition.i, result)).value,
			id.getOrElse(s"upgrade_${base.getRegistryName.getPath}_with_${addition.getRegistryName.getPath}")
		)
}