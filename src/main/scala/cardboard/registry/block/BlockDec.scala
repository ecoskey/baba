package cardboard.registry.block

import cardboard.registry.{CbRegistry, Reg, RegistryDec}
import cardboard.registry.dsl.DecMod
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour.Properties

class BlockDec[+B <: Block] private(
	val name : String,
	val props: Properties,
	val ctor : Properties => B,
	val mods : Seq[DecMod[Block]]
)(implicit registry: CbRegistry[Block]) extends Reg[Block] {
	override def reg: RegistryDec[Block] = RegistryDec(name, () => ctor(props), mods)
}

object BlockDec {

	def apply(name: String, props: Properties)(mods: DecMod[Block]*)(implicit registry: CbRegistry[Block]): BlockDec[Block] =
		new BlockDec(name, props, new Block(_), mods)

	def apply[B <: Block](name: String, ctor: Properties => B, props: Properties)(mods: DecMod[Block]*)(implicit registry: CbRegistry[Block]): BlockDec[B] =
		new BlockDec(name, props, ctor, mods)
}