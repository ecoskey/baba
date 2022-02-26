package net.emersoncoskey.cardboard.registry.item

import cats.Eval
import net.emersoncoskey.cardboard.Syntax.AllOps
import net.emersoncoskey.cardboard.registry.dsl.DecMod
import net.emersoncoskey.cardboard.registry.{Reg, RegistryDec}
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.minecraftforge.registries.{ForgeRegistries, IForgeRegistry}

/*case class CbItem[+I <: Item] private(
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

	implicit val DataGenInstance: DataGen[CbItem, Item] = new DataGen[CbItem, Item] {
		override val registry: IForgeRegistry[Item] = ForgeRegistries.ITEMS

		override def reg(r: CbItem[Item]): (String, () => Item) = (r.name, r.item)
	}
}*/

class CbItem[+I <: Item] private(
	val name : String,
	val props: Properties,
	val ctor : Properties => I,
	val mods : Seq[DecMod[Item]]
)

object CbItem {
	implicit val regInstance: Reg[CbItem, Item] = new Reg[CbItem, Item] {
		override val registry: IForgeRegistry[Item] = ForgeRegistries.ITEMS

		override def reg(r: CbItem[Item]): RegistryDec[Item] = RegistryDec(r.name, () => r.ctor(r.props), r.mods)
	}

	def apply(name: String, properties: Properties)(mods: DecMod[Item]*): CbItem[Item] =
		new CbItem(name, properties, new Item(_), mods)

	def apply[I <: Item](name: String, properties: Properties, ctor: Properties => I)(mods: DecMod[Item]*): CbItem[I] =
		new CbItem(name, properties, ctor, mods)
}
