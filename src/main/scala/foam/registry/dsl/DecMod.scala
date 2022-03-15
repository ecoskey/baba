package foam.registry.dsl

import foam.BaseMod
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.eventbus.api.{Event, EventPriority, IEventBus}

/** NOTE: end users shouldn't extend DecMod */
sealed trait DecMod[-A] {
	type E <: Event
	val eventClass: Class[E]
	val priority       : EventPriority = EventPriority.NORMAL
	val receiveCanceled: Boolean       = false

	def handleEvent(target: A, event: E, mod: BaseMod): Unit
}

trait ForgeDecMod[-A] extends DecMod[A] {
	def register(target: => A, mod: BaseMod): Unit = MinecraftForge.EVENT_BUS.addListener(priority, receiveCanceled, eventClass, e => handleEvent(target, e, mod))

}

trait ModDecMod[-A] extends DecMod[A] {
	def register(target: => A, bus: IEventBus, mod: BaseMod): Unit = bus.addListener(priority, receiveCanceled, eventClass, e => handleEvent(target, e, mod))
}




