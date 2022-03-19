package baba.registry.soundEvent

import net.minecraft.sounds.SoundEvent

//TODO: VERY WIP, not going to include for now

/** dummy generic, to make Reg instance applicable. IDK why you'd extend SoundEvent */
class SoundEventDec[+S <: SoundEvent](val name: String) extends AnyVal

object SoundEventDec {

}