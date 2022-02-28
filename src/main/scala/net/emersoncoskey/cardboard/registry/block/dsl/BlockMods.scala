package net.emersoncoskey.cardboard.registry.block.dsl

import net.emersoncoskey.cardboard.CbMod
import net.emersoncoskey.cardboard.datagen.tag.TagAssignment
import net.emersoncoskey.cardboard.datagen.tag.block.CbBlockTagsProvider
import net.emersoncoskey.cardboard.registry.dsl.{Attr, ModDecMod}
import net.minecraft.client.renderer.{ItemBlockRenderTypes, RenderType}
import net.minecraft.tags.Tag
import net.minecraft.world.level.block.Block
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent

object BlockMods {
	val renderType: Attr[Block, RenderType] = rType => new RenderTypeMod(rType)

	def tags(first: Tag.Named[Block], rest: Tag.Named[Block]*): ModDecMod[Block] = new BlockTagsMod(first :: rest.toList)
}
