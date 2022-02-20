package net.emersoncoskey.cardboard.datagen.decmod

import net.emersoncoskey.cardboard.CbMod
import net.emersoncoskey.cardboard.datagen.rendertype.{RenderTypeAssignment, RenderTypeSetupListener}
import net.emersoncoskey.cardboard.datagen.tag.TagAssignment
import net.emersoncoskey.cardboard.datagen.tag.block.CbBlockTagsProvider
import net.minecraft.client.renderer.RenderType
import net.minecraft.tags.Tag
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent


object BlockMods {
	def tags(first: Tag.Named[Block], rest: Tag.Named[Block]*): DecMod[Block, Unit] = DecMod.listener(r =>
		CbMod.EventListener[GatherDataEvent]((e, mod) => {
			val generator = e.getGenerator
			val helper = e.getExistingFileHelper
			generator.addProvider(new CbBlockTagsProvider(mod, generator, helper, TagAssignment(r, first :: rest.toList) :: Nil))
		})
	)

	val renderType: Attr[Block, RenderType, Unit] =
		rType => DecMod.listener(r => new RenderTypeSetupListener(RenderTypeAssignment(r, rType) :: Nil))

	//val props: DecMod[Block, BlockBehaviour.Properties] = DecMod(r => (None, r.))
}
