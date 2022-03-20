package baba.registry.block

import baba.registry.RegDec
import baba.registry.dsl.DecMod
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour.Properties

import java.util.function.Supplier

class BlockDec[+B <: Block] private (
	val name : String,
	props: Properties,
	ctor : Properties => B,
	override val modifiers: Seq[DecMod[Block]]
) extends RegDec[Block] {

	override def supplier: Supplier[Block] = () => ctor(props)
}

object BlockDec {
	def apply(name: String, props: Properties)(mods: DecMod[Block]*): BlockDec[Block] =
		new BlockDec(name, props, new Block(_), mods)

	def apply[B <: Block](name: String, ctor: Properties => B, props: Properties)(mods: DecMod[Block]*): BlockDec[B] =
		new BlockDec(name, props, ctor, mods)
}