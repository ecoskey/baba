package foam

import foam.data.RNil
import net.minecraftforge.fml.common.Mod

@Mod(Foam._modId)
object Foam extends BaseMod {
	final val _modId = "foam"

	type Registries = RNil
	lazy val registries: Registries = RNil

	lazy val modules: Seq[Foam.Module[_]] = Nil
}