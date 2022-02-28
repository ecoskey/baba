package cardboard.registry.dsl

import cardboard.CbMod
import net.minecraftforge.eventbus.api.{Event, EventPriority}

/** NOTE: end users shouldn't extend DecMod */
trait DecMod[-A] {
	type E <: Event
	val eventClass: Class[E]
	val priority       : EventPriority = EventPriority.NORMAL
	val receiveCanceled: Boolean       = false

	def handleEvent(target: A, event: E, mod: CbMod): Unit
}




