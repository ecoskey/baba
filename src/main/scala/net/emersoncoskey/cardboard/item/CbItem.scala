package net.emersoncoskey.cardboard.item

import net.emersoncoskey.cardboard.CbMod
import net.emersoncoskey.cardboard.Syntax.ItemOps
import net.emersoncoskey.cardboard.recipe.RecipeHaver
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.crafting.Ingredient

case class CbItem[+I <: Item] private(
	name: String,
	item: () => I,

	recipes: List[Item => RecipeHaver]
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
			private val name : String,
			private val ctor : Properties => I,
			private val props: Properties,

			private val recipes: List[Item => RecipeHaver] = Nil
		) extends Builder[I] {
			//todo: recipe providers, item model providers(generated and custom),
			def recipes(first: Item => RecipeHaver, rest: (Item => RecipeHaver)*): FinalStep[I] =
				copy(recipes = first :: rest.toList ::: recipes)

			def build: CbItem[I] = CbItem(name, () => ctor(props), recipes)
		}
	}
}
