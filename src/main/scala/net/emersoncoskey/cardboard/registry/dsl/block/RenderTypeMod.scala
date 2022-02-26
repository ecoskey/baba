package net.emersoncoskey.cardboard.registry.dsl.block

import net.emersoncoskey.cardboard.CbMod
import net.emersoncoskey.cardboard.registry.dsl.ModDecMod
import net.minecraft.client.renderer.{ItemBlockRenderTypes, RenderType}
import net.minecraft.world.level.block.Block
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

class RenderTypeMod(rType: RenderType) extends ModDecMod[Block] {
	override type E = FMLClientSetupEvent
	override val eventClass: Class[FMLClientSetupEvent] = classOf[FMLClientSetupEvent]

	override def handleEvent(target: Block, event: FMLClientSetupEvent, mod: CbMod): Unit = ItemBlockRenderTypes.setRenderLayer(target, rType)
}
