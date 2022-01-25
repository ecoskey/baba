package net.emersoncoskey.cardboard

import net.emersoncoskey.cardboard.Syntax.ItemOps
import net.emersoncoskey.cardboard.block.CbBlock
import net.emersoncoskey.cardboard.item.CbItem
import net.emersoncoskey.cardboard.recipe.craftingtable.CbShapelessRecipe
import net.minecraft.client.renderer.RenderType
import net.minecraft.world.item.{BlockItem, CreativeModeTab, Item, Items}
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.emersoncoskey.cardboard.Cardboard

object TestModule extends CbModule {
	lazy val items : Seq[CbItem[Item]]                    = Seq(Amongus)
	lazy val blocks: Seq[CbBlock[Block /*, BlockItem*/ ]] = Seq(AmongusBlock)

	val AmongusBlock: CbBlock[TestBlock /*, BlockItem*/ ] =
		CbBlock.named("amongus2_block")
		       .custom(new TestBlock(_))
		       .properties(BlockBehaviour.Properties.of(Material.STONE))
		       .renderType(RenderType.cutout)
		       .build

	val Amongus: CbItem[BlockItem] =
		CbItem.named("amongus2")
		      .custom(new BlockItem(Cardboard(AmongusBlock).get, _))
		      .properties(new Item.Properties().tab(CreativeModeTab.TAB_MISC))
		      .recipes(
			      for {
				      _ <- CbShapelessRecipe.ingredients(
					      Items.DIAMOND.i -> 2
				      )
				      recipe <- CbShapelessRecipe.build
			      } yield recipe
		      )
		      .build
}
