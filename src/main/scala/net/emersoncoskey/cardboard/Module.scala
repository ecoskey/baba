package net.emersoncoskey.cardboard

import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus

@EventBusSubscriber(modid = ModId, bus = Bus.MOD)
abstract class Module(val ModId: String) {

}
