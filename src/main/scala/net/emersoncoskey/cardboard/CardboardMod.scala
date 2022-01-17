package net.emersoncoskey.cardboard

import net.minecraftforge.eventbus.api.IEventBus
import org.apache.logging.log4j.Logger

abstract class CardboardMod {
	protected val _modId: String

	final def ModId: String = _modId

	val EventBus: IEventBus
	val Logger  : Logger
	val Modules : Seq[CardboardModule]

	Modules.foreach(EventBus.register(_))
}