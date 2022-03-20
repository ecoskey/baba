package baba.registry.block.dsl

import baba.registry.dsl.{Attr, ModDecMod}
import net.minecraft.client.renderer.RenderType
import net.minecraft.tags.{Tag, TagKey}
import net.minecraft.world.level.block.Block

trait BlockMods {
	val renderType: Attr[Block, RenderType] = rType => new RenderTypeMod(rType)

	def tags(first: TagKey[Block], rest: TagKey[Block]*): ModDecMod[Block] = new BlockTagsMod(first :: rest.toList)
}
