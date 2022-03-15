package foam.registry.block.dsl

import foam.registry.dsl.{Attr, ModDecMod}
import net.minecraft.client.renderer.RenderType
import net.minecraft.tags.Tag
import net.minecraft.world.level.block.Block

trait BlockMods {
	val renderType: Attr[Block, RenderType] = rType => new RenderTypeMod(rType)

	def tags(first: Tag.Named[Block], rest: Tag.Named[Block]*): ModDecMod[Block] = new BlockTagsMod(first :: rest.toList)
}
