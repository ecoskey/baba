package foam.registry.soundEvent

import net.minecraft.sounds.SoundEvent

//TODO: VERY WIP, not going to include for now

/** dummy generic, to make Reg instance applicable. IDK why you'd extend SoundEvent */
case class SoundEventDec[+S <: SoundEvent](name: String) extends AnyVal

object SoundEventDec {

}