package net.emersoncoskey.cardboard.recipe

import cats.data.State
import net.emersoncoskey.cardboard.CbMod
import net.minecraft.advancements.CriterionTriggerInstance
import net.minecraft.data.recipes.{FinishedRecipe, RecipeBuilder, ShapelessRecipeBuilder}
import net.minecraft.resources.ResourceLocation

import java.util.function.Consumer

abstract class CbRecipeBuilderRecipe[B <: RecipeBuilder](
	internal: B,
	id: Option[String],
) extends CbRecipe {
	private[cardboard] def save(consumer: Consumer[FinishedRecipe], mod: CbMod): Unit = id match {
		case Some(s) => internal.save(consumer, new ResourceLocation(mod.ModId, s))
		case None => internal.save(consumer)
	}
}
