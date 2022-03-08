package cardboard.registry.soundEvent

import cardboard.CbMod
import cardboard.registry.{Reg, RegistryDec}
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraftforge.registries.{ForgeRegistries, IForgeRegistry}

/** dummy generic, to make Reg instance applicable. IDK why you'd extend SoundEvent */
case class SoundEventDec[+S <: SoundEvent](name: String) extends AnyVal

object SoundEventDec {
	/*implicit val regInstance: Reg[SoundEventDec, SoundEvent] = new Reg[SoundEventDec, SoundEvent] {
		val registry: IForgeRegistry[SoundEvent] = ForgeRegistries.SOUND_EVENTS

		def reg(r: SoundEventDec[SoundEvent], mod: CbMod): RegistryDec[SoundEvent] =
			RegistryDec(r.name, () => new SoundEvent(new ResourceLocation(mod.ModId, r.name)))
	}*/
}