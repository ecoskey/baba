package net.emersoncoskey.cardboard.item

import net.emersoncoskey.cardboard.CbMod
import net.emersoncoskey.cardboard.Syntax.ItemOps
import net.emersoncoskey.cardboard.model.CbModel
import net.emersoncoskey.cardboard.recipe.CbRecipe
import net.minecraft.tags.Tag
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.crafting.Ingredient
import net.minecraftforge.client.model.generators.ItemModelBuilder

case class CbItem[+I <: Item] private(
	name   : String,
	item   : () => I,

	recipes: List[Item => CbRecipe],
	tags   : List[Tag.Named[Item]],
	model  : Option[String => CbModel[ItemModelBuilder]],
) {
	def i(implicit mod: CbMod): Ingredient = mod(this).get.i
}

object CbItem {
	def named(name: String): Builder.FirstStep = Builder.FirstStep(name)

	sealed trait Builder[+I <: Item]

	object Builder {
		case class FirstStep private(private val name: String) extends Builder[Item] {
			def custom[I <: Item](ctor: Properties => I): SecondStep[I] = SecondStep(name, ctor)

			def properties(props: Properties): FinalStep[Item] = FinalStep(name, new Item(_), props)
		}

		case class SecondStep[+I <: Item] private(
			private val name: String,
			private val ctor: Properties => I,
		) extends Builder[I] {
			def properties(props: Properties): FinalStep[I] = FinalStep(name, ctor, props)
		}

		case class FinalStep[+I <: Item] private(
			private val name   : String,
			private val ctor   : Properties => I,
			private val props  : Properties,

			private val recipes: List[Item => CbRecipe] = Nil,
			private val tags   : List[Tag.Named[Item]] = Nil,
			private val model  : Option[String => CbModel[ItemModelBuilder]] = None
		) extends Builder[I] {
			def recipes(first: Item => CbRecipe, rest: (Item => CbRecipe)*): FinalStep[I] =
				copy(recipes = first :: rest.toList ::: recipes)

			def tags(first: Tag.Named[Item], rest: Tag.Named[Item]*): FinalStep[I] =
				copy(tags = first :: rest.toList ::: tags)

			def model(model: String => CbModel[ItemModelBuilder]): FinalStep[I] =
				copy(model = Some(model))

			def build: CbItem[I] = CbItem(name, () => ctor(props), recipes, tags, model)
		}
	}
}
