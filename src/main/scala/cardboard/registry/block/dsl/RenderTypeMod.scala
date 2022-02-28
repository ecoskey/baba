package cardboard.registry.block.dsl

import cardboard.CbMod
import cardboard.registry.dsl.ModDecMod
import net.minecraft.client.renderer.{ItemBlockRenderTypes, RenderType}
import net.minecraft.world.level.block.Block
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

class RenderTypeMod(rType: RenderType) extends ModDecMod[Block] {
	type E = FMLClientSetupEvent
	val eventClass: Class[FMLClientSetupEvent] = classOf[FMLClientSetupEvent]

	def handleEvent(target: Block, event: FMLClientSetupEvent, mod: CbMod): Unit = ItemBlockRenderTypes.setRenderLayer(target, rType)
}
