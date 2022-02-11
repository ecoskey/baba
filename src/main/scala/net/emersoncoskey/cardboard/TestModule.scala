package net.emersoncoskey.cardboard

import net.emersoncoskey.cardboard.datagen.decmod._
import net.emersoncoskey.cardboard.datagen.recipe.craftingtable.{CbShapedRecipe, CbShapelessRecipe}
import net.emersoncoskey.cardboard.registry.block.CbBlock
import net.emersoncoskey.cardboard.registry.item.CbItem
import net.minecraft.data.DataProvider
import net.minecraft.world.item.{BlockItem, Item, Items}
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material


object TestModule extends CbModule {
	lazy val items : Seq[CbItem[Item]]   = Seq(Amongus)
	lazy val blocks: Seq[CbBlock[Block]] = Seq(AmongusBlock)
	lazy val data  : Seq[DataProvider]   = Nil

	/*val AmongusBlock: CbBlock[TestBlock] =
		CbBlock.named("amongus_block")
		       .custom(new TestBlock(_))
		       .properties(BlockBehaviour.Properties.of(Material.STONE))
		       .tags(Tags.Blocks.ORES)
		       .renderType(RenderType.cutout)
		       .build

	val Amongus: CbItem[BlockItem] =
		CbItem.named("amongus")
		      .custom(new BlockItem(Cardboard(AmongusBlock).get, _))
		      .properties(new Item.Properties().tab(TestCreativeTab))
		      .tags(Tags.Items.INGOTS_NETHERITE)
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
		      .build*/

	val AmongusBlock: CbBlock[TestBlock] = CbBlock("amongus_block", BlockBehaviour.Properties.of(Material.STONE), new TestBlock(_))()
	val Amongus     : CbItem[BlockItem]  = CbItem("amongus", new Item.Properties().tab(TestCreativeTab), new BlockItem(Cardboard(AmongusBlock).get, _))(
		I.Recipes(CbShapedRecipe.packing3x3(Items.DIRT), CbShapelessRecipe.conversion(Items.BONE)(_))
	)
}
