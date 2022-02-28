package cardboard.registry.block.dsl

import cardboard.CbMod
import cardboard.datagen.tag.TagAssignment
import cardboard.datagen.tag.block.CbBlockTagsProvider
import cardboard.registry.dsl.{Attr, ModDecMod}
import net.emersoncoskey.cardboard.registry.dsl.Attr
import net.minecraft.client.renderer.{ItemBlockRenderTypes, RenderType}
import net.minecraft.tags.Tag
import net.minecraft.world.level.block.Block
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent

object BlockMods {
	val renderType: Attr[Block, RenderType] = rType => new RenderTypeMod(rType)

	def tags(first: Tag.Named[Block], rest: Tag.Named[Block]*): ModDecMod[Block] = new BlockTagsMod(first :: rest.toList)
}
