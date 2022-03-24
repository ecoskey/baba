package net.emersoncoskey.baba

import net.emersoncoskey.baba.data.{%:, DNil}
import net.emersoncoskey.baba.modules.TestModule
import net.emersoncoskey.baba.registry.painting.PaintingDec
import net.emersoncoskey.baba.util.{DefaultRegistries, PaintingType}
import net.minecraftforge.fml.common.Mod

/** An example implementation of BaseMod, and the entrypoint for this mod. */
@Mod(Baba._modId)
object Baba extends BaseMod {
	final val _modId = "baba"

	type Registries = DefaultRegistries
	lazy val registries: Registries = defaultRegistries

	lazy val modules: Seq[Module[_]] = TestModule.Module :: Nil
}
