package net.emersoncoskey.cardboard

import net.emersoncoskey.cardboard.registry.dsl._
import net.emersoncoskey.cardboard.datagen.recipe.CbRecipe
import net.emersoncoskey.cardboard.datagen.recipe.craftingtable.{CbShapedRecipe, CbShapelessRecipe}
import net.emersoncoskey.cardboard.datagen.recipe.furnace.CbFurnaceRecipe
import net.emersoncoskey.cardboard.registry.block.CbBlock
import net.emersoncoskey.cardboard.registry.item.CbItem
import net.emersoncoskey.cardboard.Syntax._
import net.emersoncoskey.cardboard.registry.potion.CbPotion
import net.minecraft.data.DataProvider
import net.minecraft.world.effect.{MobEffectInstance, MobEffects}
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.{BlockItem, Item, Items, PotionItem}
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraftforge.common.Tags


object TestModule extends CbModule {
	lazy val items : Seq[CbItem[Item]]   = Seq(Amongus)
	lazy val blocks: Seq[CbBlock[Block]] = Seq(AmongusBlock)
	lazy val potions: Seq[CbPotion[Potion]] = Seq(SussyPotion)

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

	val AmongusBlock: CbBlock[Block] = CbBlock("amongus_block", new TestBlock(_), BlockBehaviour.Properties.of(Material.STONE))(
		B.tags(Tags.Blocks.ORES_DIAMOND)
	)

	val Amongus: CbItem[BlockItem] = CbItem("amongus", new BlockItem(Cardboard(AmongusBlock).get, _), new Item.Properties().tab(TestCreativeTab))(
		I.recipes(
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
				_ <- CbShapedRecipe.unlockedBy("has_diamonds_and_dirt", CbRecipe.has(Items.DIAMOND, Items.DIRT))
			} yield ()),

			CbFurnaceRecipe.smelting(Items.DIAMOND.i, _, 1000000f, 100)(for {
				_ <- CbFurnaceRecipe.group("among")
				_ <- CbFurnaceRecipe.unlockedByItem(Items.DIAMOND)
			} yield ()),

			CbShapedRecipe.packing3x3(Items.DIRT)
		)
	)

	val SussyPotion: CbPotion[Potion] = CbPotion("sussy", new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 3600))
}
