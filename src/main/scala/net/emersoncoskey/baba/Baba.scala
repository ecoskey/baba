package net.emersoncoskey.baba

import net.emersoncoskey.baba.data.{%:, DNil}
import net.emersoncoskey.baba.registry.painting.PaintingDec
import net.emersoncoskey.baba.util.{DefaultRegistries, PaintingType}
import net.minecraftforge.fml.common.Mod

/** An example implementation of BaseMod, and the entrypoint for this mod. */
@Mod(Baba._modId)
object Baba extends BaseMod {
	final val _modId = "baba"

	type Registries = DefaultRegistries
	lazy val registries: Registries = defaultRegistries

	lazy val modules: Seq[Module[_]] = TestModule :: AllTheStuff :: Nil

	//doing this as an inner object for compactness
	object AllTheStuff extends Baba.Module[PaintingType %: DNil] {
		protected lazy val declarations: PaintingType %: DNil = Seq(BabaPainting) %: DNil

		val BabaPainting: PaintingDec = PaintingDec("baba", 16, 16)
	}
}