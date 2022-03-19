package baba

import baba.BaseMod.DefaultRegistries
import net.minecraftforge.fml.common.Mod

/** An example implementation of BaseMod, and the entrypoint for this mod. */
@Mod(Baba._modId)
object Baba extends BaseMod {
	final val _modId = "baba"

	type Registries = DefaultRegistries
	lazy val registries: Registries = defaultRegistries

	lazy val modules: Seq[Module[_]] = TestModule :: Nil
}