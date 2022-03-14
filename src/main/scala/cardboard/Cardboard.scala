package cardboard

import cardboard.CbMod.DefaultRegistries
import net.minecraftforge.fml.common.Mod

@Mod(Cardboard.modId)
object Cardboard extends CbMod {
	protected final val modId = "cardboard"

	protected def modules: Seq[CbModule] = TestModule :: Nil

	override type Registries = DefaultRegistries
	lazy val registries: Registries = defaultRegistries
}
