package foam.registry.block

import foam.registry.{RegDec}
import foam.registry.dsl.DecMod
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour.Properties

import java.util.function.Supplier

class BlockDec[+B <: Block] private (
	val name : String,
	val props: Properties,
	val ctor : Properties => B,
	override val mods : Seq[DecMod[Block]]
) extends RegDec[Block] {

	override def sup: Supplier[Block] = () => ctor(props)
}

object BlockDec {
	def apply(name: String, props: Properties)(mods: DecMod[Block]*): BlockDec[Block] =
		new BlockDec(name, props, new Block(_), mods)

	def apply[B <: Block](name: String, ctor: Properties => B, props: Properties)(mods: DecMod[Block]*): BlockDec[B] =
		new BlockDec(name, props, ctor, mods)
}