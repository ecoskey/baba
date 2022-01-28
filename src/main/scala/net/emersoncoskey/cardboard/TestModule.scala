package net.emersoncoskey.cardboard

import net.emersoncoskey.cardboard.Syntax.ItemOps
import net.emersoncoskey.cardboard.block.CbBlock
import net.emersoncoskey.cardboard.item.CbItem
import net.emersoncoskey.cardboard.recipe.CbRecipe
import net.emersoncoskey.cardboard.recipe.craftingtable.{CbShapedRecipe, CbShapelessRecipe}
import net.emersoncoskey.cardboard.recipe.furnace.CbFurnaceRecipe
import net.minecraft.client.renderer.RenderType
import net.minecraft.world.item.{BlockItem, CreativeModeTab, Item, Items}
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material


object TestModule extends CbModule {
	lazy val items : Seq[CbItem[Item]]   = Seq(Amongus)
	lazy val blocks: Seq[CbBlock[Block]] = Seq(AmongusBlock)

	val AmongusBlock: CbBlock[TestBlock] =
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
			      CbShapelessRecipe(_, 5, Some("cringe_recipe"))(for {
				      _ <- CbShapelessRecipe.ingredients(Items.DIRT.i -> 4)
				      _ <- CbShapelessRecipe.group("among")
				      _ <- CbShapelessRecipe.unlockedByItem(Items.DIRT)
			      } yield ()),

			      CbShapedRecipe(_, 64, Some("better_recipe"))(for {
				      a <- CbShapedRecipe.define('X', Items.DIAMOND.i)
				      b <- CbShapedRecipe.define('/', Items.DIRT.i)
				      _ <- CbShapedRecipe.pattern(List(b, a, b), List(a, a, a), List(b, a, b))
				      _ <- CbShapedRecipe.group("among")
				      _ <- CbShapedRecipe.unlockedBy(
					      "has_diamonds_and_dirt",
					      CbRecipe.has(Items.DIAMOND, Items.DIRT)
				      )
			      } yield ()),

			      CbFurnaceRecipe.smelting(Items.DIAMOND.i, _, 1000000f, 100)(for {
				      _ <- CbFurnaceRecipe.group("among")
				      _ <- CbFurnaceRecipe.unlockedByItem(Items.DIAMOND)
			      } yield ()),

			      CbShapedRecipe.packing3x3(Items.DIRT)
		      )
		      .build
}
