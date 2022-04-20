package net.emersoncoskey.baba.registry.mods

import net.emersoncoskey.baba.registry.{EventHandler, SimpleAttr}
import net.minecraft.client.renderer.{ItemBlockRenderTypes, RenderType}
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

trait FluidMods {
	val fluidRenderType: SimpleAttr[RenderType, Fluid] = renderType => fluidReg =>
		EventHandler.Mod[FMLClientSetupEvent](classOf, (_, _) => ItemBlockRenderTypes.setRenderLayer(fluidReg.get, renderType))
}
