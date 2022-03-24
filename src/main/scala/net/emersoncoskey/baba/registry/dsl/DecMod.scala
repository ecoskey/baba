package net.emersoncoskey.baba.registry.dsl

import net.emersoncoskey.baba.BaseMod
import net.minecraftforge.eventbus.api.{Event, EventPriority}

/** a Declaration Modifier */
sealed trait DecMod[-A] {
	type E <: Event
	val eventClass: Class[E]
	val priority: EventPriority = EventPriority.NORMAL
	val receiveCanceled: Boolean = false

	def handleEvent(target: A, event: E, mod: BaseMod): Unit
}

/** A marker trait signifying that this is meant to be put on the Forge event bus */
trait ForgeDecMod[-A] extends DecMod[A]

/** A marker trait signifying that this is meant to be put on the Mod event bus */
trait ModDecMod[-A] extends DecMod[A]




