package net.emersoncoskey.cardboard

import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import org.apache.logging.log4j.Logger

abstract class Mod {
	val ModId   : String
	val EventBus: IEventBus
	val Logger  : Logger

	val Modules: Seq[Module]

	Modules.foreach(module => {
		Logger.info(module)
		EventBus.register(module)
	})
}
