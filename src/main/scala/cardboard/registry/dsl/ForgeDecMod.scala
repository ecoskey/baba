package cardboard.registry.dsl

import cardboard.CbMod
import net.minecraftforge.common.MinecraftForge

trait ForgeDecMod[-A] extends DecMod[A] {
	final def busRegister(target: => A, mod: CbMod): Unit =
		MinecraftForge.EVENT_BUS.addListener[E](priority, receiveCanceled, eventClass, (event: E) => handleEvent(target, event, mod))
}
