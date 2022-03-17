package foam

import foam.BaseMod.DefaultRegistries
import net.minecraftforge.fml.common.Mod

@Mod(Foam._modId)
object Foam extends BaseMod {
	final val _modId = "foam"

	type Registries = DefaultRegistries
	lazy val registries: Registries = defaultRegistries

	lazy val modules: Seq[Module[_]] = TestModule :: Nil
}