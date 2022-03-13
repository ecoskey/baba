package cardboard

import net.minecraftforge.fml.common.Mod

@Mod(Cardboard._modId)
object Cardboard extends CbMod[CbMod.DefaultRegistryTypes] {
	final val _modId = "cardboard"

	protected def modules: Seq[CbModule[RegistryTypes]] = TestModule :: Nil

	lazy val registries: Registries = defaultRegistries
}
