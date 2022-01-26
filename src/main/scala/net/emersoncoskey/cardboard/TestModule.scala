package net.emersoncoskey.cardboard

import net.emersoncoskey.cardboard.Syntax.ItemOps
import net.emersoncoskey.cardboard.block.CbBlock
import net.emersoncoskey.cardboard.item.CbItem
import net.emersoncoskey.cardboard.recipe.craftingtable.{CbShapedRecipe, CbShapelessRecipe}
import net.minecraft.client.renderer.RenderType
import net.minecraft.world.item.{BlockItem, CreativeModeTab, Item, Items}
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance._
import net.emersoncoskey.cardboard.recipe.craftingtable.CbShapedRecipe.IngredientKey.Empty


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
			      CbShapelessRecipe(5, Some("cringe_recipe"))(for {
				      _ <- CbShapelessRecipe.ingredients(Items.DIRT.i -> 4)
				      _ <- CbShapelessRecipe.group("among")
				      _ <- CbShapelessRecipe.unlockedBy("has_material", hasItems(Items.DIRT))
			      } yield ()),

			      CbShapedRecipe(64, Some("better_recipe"))(for {
				      a <- CbShapedRecipe.define('X', Items.DIAMOND.i)
				      b <- CbShapedRecipe.define('/', Items.DIRT.i)
				      _ <- CbShapedRecipe.pattern(
					      List(b, a, b),
					      List(a, a, a),
					      List(b, a, b),
				      )
				      _ <- CbShapedRecipe.group("among")
				      _ <- CbShapedRecipe.unlockedBy("has_material", hasItems(Items.DIAMOND))
			      } yield ())
		      )
		      .build
}
