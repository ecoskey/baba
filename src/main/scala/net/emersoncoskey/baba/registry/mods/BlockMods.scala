package net.emersoncoskey.baba.registry.mods

import net.emersoncoskey.baba.registry.{EventHandler, SimpleAttr}
import net.minecraft.client.renderer.{ItemBlockRenderTypes, RenderType}
import net.minecraft.world.level.block.Block
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

trait BlockMods {
	val blockRenderType: SimpleAttr[RenderType, Block] = renderType => blockReg =>
		EventHandler.Mod[FMLClientSetupEvent](classOf[FMLClientSetupEvent], _ => ItemBlockRenderTypes.setRenderLayer(blockReg.get, renderType))
}
