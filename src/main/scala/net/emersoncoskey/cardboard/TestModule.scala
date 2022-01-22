package net.emersoncoskey.cardboard

import net.emersoncoskey.cardboard.block.CbBlock
import net.emersoncoskey.cardboard.item.CbItem
import net.emersoncoskey.cardboard.recipe.CbShapelessRecipe
import net.minecraft.world.item.{BlockItem, CreativeModeTab, Item}
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material

object TestModule extends CbModule {
	lazy val Items : Seq[CbItem[Item]]   = Seq(Amongus)
	lazy val Blocks: Seq[CbBlock[Block]] = Seq(AmongusBlock)

	lazy val AmongusBlock: CbBlock[TestBlock] =
		CbBlock.named("amongus2_block")
		       .custom(new TestBlock(_))
		       .properties(BlockBehaviour.Properties.of(Material.STONE))
		       .build

	lazy val Amongus: CbItem[BlockItem] =
		CbItem.named("amongus2")
		      .custom(new BlockItem(Cardboard(AmongusBlock).get, _))
		      .properties(new Item.Properties().tab(CreativeModeTab.TAB_MISC))
		      .recipes(
			      CbShapelessRecipe
			        .named("amongus")
			        .ingredients(null -> -1)
			        .result(3),

			      CbShapelessRecipe
			        .named("among2")
			        .ingredients(
				        null -> 2,
				        null,
				        null
			        )
			        .result(1492)
		      )
		      .build

}
