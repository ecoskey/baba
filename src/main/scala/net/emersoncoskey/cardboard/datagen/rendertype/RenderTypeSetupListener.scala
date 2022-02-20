package net.emersoncoskey.cardboard.datagen.rendertype

import net.emersoncoskey.cardboard.CbMod
import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.world.level.block.Block
import net.minecraftforge.fml.event.lifecycle.{FMLClientSetupEvent, FMLCommonSetupEvent}

class RenderTypeSetupListener(val assignments: Seq[RenderTypeAssignment[Block]]) extends CbMod.EventListener {
	override type E = FMLCommonSetupEvent

	override def handleEvent(event: E, mod: CbMod): Unit =
		assignments.foreach(b => ItemBlockRenderTypes.setRenderLayer(b.obj, b.renderType))
}