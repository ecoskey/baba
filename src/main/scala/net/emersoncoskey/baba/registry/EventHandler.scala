package net.emersoncoskey.baba.registry

import net.emersoncoskey.baba.BaseMod
import net.minecraftforge.eventbus.api.{Event, EventPriority}

sealed trait EventHandler {
	type E <: Event
	val filter          : Class[E]
	val handler         : (E, BaseMod) => Unit
	val priority        : EventPriority
	val receiveCancelled: Boolean
}

object EventHandler {
	trait Mod extends EventHandler

	object Mod {
		type Aux[X] = EventHandler.Mod { type E = X }

		def apply[e <: Event](
			_filter          : Class[e],
			_handler         : (e, BaseMod) => Unit,
			_priority        : EventPriority = EventPriority.NORMAL,
			_receiveCancelled: Boolean = false
		): EventHandler.Mod.Aux[e] = new EventHandler.Mod {
			type E = e
			val filter          : Class[E]             = _filter
			val handler         : (E, BaseMod) => Unit = _handler
			val priority        : EventPriority        = _priority
			val receiveCancelled: Boolean              = _receiveCancelled
		}

		def unapply(handler: EventHandler.Mod): Option[(Class[handler.E], (handler.E, BaseMod) => Unit, EventPriority, Boolean)] =
			Some(handler.filter, handler.handler, handler.priority, handler.receiveCancelled)
	}

	trait Forge extends EventHandler

	object Forge {
		type Aux[X] = EventHandler.Forge { type E = X }

		def apply[e <: Event](
			_filter          : Class[e],
			_handler         : (e, BaseMod) => Unit,
			_priority        : EventPriority = EventPriority.NORMAL,
			_receiveCancelled: Boolean = false
		): EventHandler.Forge.Aux[e] = new EventHandler.Forge {
			type E = e
			val filter          : Class[E]             = _filter
			val handler         : (E, BaseMod) => Unit = _handler
			val priority        : EventPriority        = _priority
			val receiveCancelled: Boolean              = _receiveCancelled
		}

		def unapply(handler: EventHandler.Forge): Option[(Class[handler.E], (handler.E, BaseMod) => Unit, EventPriority, Boolean)] =
			Some(handler.filter, handler.handler, handler.priority, handler.receiveCancelled)
	}
}