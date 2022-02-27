package net.emersoncoskey.cardboard.registry.dsl

import net.emersoncoskey.cardboard.CbMod
import net.minecraftforge.eventbus.api.{Event, IEventBus}
import net.minecraftforge.fml.event.IModBusEvent

trait ModDecMod[-A] extends DecMod[A] {
	type E <: Event with IModBusEvent

	final def busRegister(target: => A, bus: IEventBus, mod: CbMod): Unit =
		bus.addListener[E](priority, receiveCanceled, eventClass, (event: E) => handleEvent(target, event, mod))
}
