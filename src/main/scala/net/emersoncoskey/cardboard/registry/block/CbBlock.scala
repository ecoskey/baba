package net.emersoncoskey.cardboard.registry.block

import cats.Eval
import net.emersoncoskey.cardboard.registry.dsl.DecMod
import net.emersoncoskey.cardboard.registry.{Reg, RegistryDec}
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraftforge.registries.{ForgeRegistries, IForgeRegistry}

/*
case class CbBlock[+B <: Block] private(
	name      : String,
	block     : () => B,

	renderType: RenderType,
	tags: List[Tag.Named[Block]],
	model: CbModel[BlockModelBuilder]
)

object CbBlock {
	def named(name: String): Builder.FirstStep = Builder.FirstStep(name)

	sealed trait Builder[+B <: Block]

	object Builder {
		case class FirstStep private(private val name: String) extends Builder[Block] {
			def custom[B <: Block](ctor: Properties => B): SecondStep[B] =
				SecondStep(name, ctor)

			def properties(properties: Properties): FinalStep[Block] =
				FinalStep(name, new Block(_), properties)
		}

		case class SecondStep[+B <: Block] private(
			private val name: String,
			private val ctor: Properties => B
		) extends Builder[B] {
			def properties(props: Properties): FinalStep[B] =
				FinalStep(name, ctor, props)
		}

		case class FinalStep[+B <: Block] private(
			private val name      : String,
			private val ctor      : Properties => B,
			private val props     : Properties,

			private val renderType: RenderType = RenderType.solid,
			private val tags: List[Tag.Named[Block]] = Nil,
		) extends Builder[B] {
			//todo: recipe providers, Block model providers(generated and custom),

			def renderType(renderType: RenderType): FinalStep[B] =
				copy(renderType = renderType)

			def tags(first: Tag.Named[Block], rest: Tag.Named[Block]*): FinalStep[B] =
				copy(tags = first :: rest.toList ::: tags)

			def build: CbBlock[B] =
				CbBlock(name, () => ctor(props), renderType, tags)
		}
	}
}*/

class CbBlock[+B <: Block] private(
	val name : String,
	val props: Properties,
	val ctor : Properties => B,
	val mods : Seq[DecMod[Block]]
)

object CbBlock {
	implicit val r: Reg[CbBlock, Block] = new Reg[CbBlock, Block] {
		override val registry: IForgeRegistry[Block] = ForgeRegistries.BLOCKS

		override def reg(r: CbBlock[Block]): RegistryDec[Block] = RegistryDec(r.name, () => r.ctor(r.props), r.mods)
	}

	def apply(name: String, props: Properties)(mods: DecMod[Block]*): CbBlock[Block] =
		new CbBlock(name, props, new Block(_), mods)

	def apply[B <: Block](name: String, ctor: Properties => B, props: Properties)(mods: DecMod[Block]*): CbBlock[B] =
		new CbBlock(name, props, ctor, mods)
}