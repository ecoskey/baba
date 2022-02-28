package cardboard.registry.soundEvent

import cardboard.CbMod
import cardboard.registry.{Reg, RegistryDec}
import net.emersoncoskey.cardboard.registry.Reg
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraftforge.registries.{ForgeRegistries, IForgeRegistry}

/** dummy generic, to make Reg instance applicable. IDK why you'd extend SoundEvent */
case class CbSoundEvent[+S <: SoundEvent](name: String) extends AnyVal

object CbSoundEvent {
	implicit val regInstance: Reg[CbSoundEvent, SoundEvent] = new Reg[CbSoundEvent, SoundEvent] {
		override val registry: IForgeRegistry[SoundEvent] = ForgeRegistries.SOUND_EVENTS

		override def reg(r: CbSoundEvent[SoundEvent], mod: CbMod): RegistryDec[SoundEvent] =
			RegistryDec(r.name, () => new SoundEvent(new ResourceLocation(mod.ModId, r.name)))
	}
}