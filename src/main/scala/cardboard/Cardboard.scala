package cardboard

import cardboard.data.{#:, RNil}
import cardboard.syntax.reglist._
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.fml.common.Mod

@Mod(Cardboard._modId)
object Cardboard extends CbMod {
	final val _modId = "cardboard"

	type Registries = Item #: Block #: RNil
	lazy val registries: Registries = getItemReg #: getBlockReg #: RNil

	lazy val modules: Seq[Cardboard.Module[_]] = TestModule :: Nil
}