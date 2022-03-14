package cardboard

import cardboard.CbMod.DefaultRegistries
import net.minecraftforge.fml.common.Mod

@Mod(Cardboard._modId)
object Cardboard extends CbMod {
	final val _modId = "cardboard"

	type Registries = DefaultRegistries
	lazy val registries: Registries = defaultRegistries

	lazy val modules: Seq[Cardboard.Module[_]] = TestModule :: Nil
}
