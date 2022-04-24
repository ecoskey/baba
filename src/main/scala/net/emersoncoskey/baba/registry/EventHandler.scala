package net.emersoncoskey.baba.registry

import net.minecraftforge.eventbus.api.{Event, EventPriority, GenericEvent}

sealed trait EventHandler

object EventHandler {
	sealed trait Normal extends EventHandler {
		type E <: Event
		val filter          : Class[E]
		val handler         : E => Unit
		val priority        : EventPriority
		val receiveCancelled: Boolean
	}

	trait Mod extends EventHandler.Normal

	object Mod {
		type Aux[X] = EventHandler.Mod {type E = X}

		def apply[e <: Event](
			_filter          : Class[e],
			_handler         : e => Unit,
			_priority        : EventPriority = EventPriority.NORMAL,
			_receiveCancelled: Boolean = false
		): EventHandler.Mod.Aux[e] = new EventHandler.Mod {
			type E = e
			val filter          : Class[E]      = _filter
			val handler         : E => Unit     = _handler
			val priority        : EventPriority = _priority
			val receiveCancelled: Boolean       = _receiveCancelled
		}

		def unapply(handler: EventHandler.Mod): Option[(Class[handler.E], handler.E => Unit, EventPriority, Boolean)] =
			Some(handler.filter, handler.handler, handler.priority, handler.receiveCancelled)
	}

	trait Forge extends EventHandler.Normal

	object Forge {
		type Aux[X] = EventHandler.Forge {type E = X}

		def apply[e <: Event](
			_filter          : Class[e],
			_handler         : e => Unit,
			_priority        : EventPriority = EventPriority.NORMAL,
			_receiveCancelled: Boolean = false
		): EventHandler.Forge.Aux[e] = new EventHandler.Forge {
			type E = e
			val filter          : Class[E]      = _filter
			val handler         : E => Unit     = _handler
			val priority        : EventPriority = _priority
			val receiveCancelled: Boolean       = _receiveCancelled
		}

		def unapply(handler: EventHandler.Forge): Option[(Class[handler.E], handler.E => Unit, EventPriority, Boolean)] =
			Some(handler.filter, handler.handler, handler.priority, handler.receiveCancelled)
	}

	// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	sealed trait Generic extends EventHandler {
		type E[X] <: GenericEvent[X]
		type A
		val eventFilter     : Class[E[A]]
		val paramFilter     : Class[A]
		val handler         : E[A] => Unit
		val priority        : EventPriority
		val receiveCancelled: Boolean
	}

	trait GenericMod extends EventHandler.Generic

	object GenericMod {
		type Aux[e[x] <: GenericEvent[x], a] = EventHandler.GenericMod {
			type E[X] = e[X]
			type A = a
		}

		def apply[e[x] <: GenericEvent[x], a](
			_eventFilter     : Class[e[a]],
			_paramFilter     : Class[a],
			_handler         : e[a] => Unit,
			_priority        : EventPriority = EventPriority.NORMAL,
			_receiveCancelled: Boolean = false
		): EventHandler.GenericMod.Aux[e, a] = new GenericMod {
			type E[X] = e[X]
			type A = a
			val eventFilter     : Class[E[A]]   = _eventFilter
			val paramFilter     : Class[A]      = _paramFilter
			val handler         : E[A] => Unit  = _handler
			val priority        : EventPriority = _priority
			val receiveCancelled: Boolean       = _receiveCancelled
		}

		def unapply(h: EventHandler.GenericMod): Option[(Class[h.E[h.A]], Class[h.A], h.E[h.A] => Unit, EventPriority, Boolean)] =
			Some(h.eventFilter, h.paramFilter, h.handler, h.priority, h.receiveCancelled)
	}

	trait GenericForge extends EventHandler.Generic

	object GenericForge {
		type Aux[e[x] <: GenericEvent[x], a] = EventHandler.GenericForge {
			type E[X] = e[X]
			type A = a
		}

		def apply[e[x] <: GenericEvent[x], a](
			_eventFilter     : Class[e[a]],
			_paramFilter     : Class[a],
			_handler         : e[a] => Unit,
			_priority        : EventPriority = EventPriority.NORMAL,
			_receiveCancelled: Boolean = false
		): EventHandler.GenericForge.Aux[e, a] = new EventHandler.GenericForge {
			type E[X] = e[X]
			type A = a
			val eventFilter     : Class[E[A]]   = _eventFilter
			val paramFilter     : Class[A]      = _paramFilter
			val handler         : E[A] => Unit  = _handler
			val priority        : EventPriority = _priority
			val receiveCancelled: Boolean       = _receiveCancelled
		}

		def unapply(h: EventHandler.GenericForge): Option[(Class[h.E[h.A]], Class[h.A], h.E[h.A] => Unit, EventPriority, Boolean)] =
			Some(h.eventFilter, h.paramFilter, h.handler, h.priority, h.receiveCancelled)
	}
}