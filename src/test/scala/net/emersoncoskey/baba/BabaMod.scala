package net.emersoncoskey.baba

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus

/** An example implementation of BaseMod, and the entrypoint for this mod. */
@Mod(BabaMod.ModId)
@EventBusSubscriber(modid = BabaMod.ModId, bus = Bus.MOD)
object BabaMod {
	final val ModId = "baba"
	val baba: Baba = Baba(ModId, TestModule.module)
}
