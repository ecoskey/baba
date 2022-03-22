package net.emersoncoskey.baba

import net.emersoncoskey.baba.data.{%:, DNil}
import net.emersoncoskey.baba.datagen.recipe.BabaRecipe
import net.emersoncoskey.baba.datagen.recipe.craftingtable.{BabaShapedRecipe, BabaShapelessRecipe}
import net.emersoncoskey.baba.datagen.recipe.furnace.BabaFurnaceRecipe
import net.emersoncoskey.baba.registry.block.BlockDec
import net.minecraft.world.item.{BlockItem, Item, Items}
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraftforge.common.Tags
import net.emersoncoskey.baba.syntax.all._
import net.emersoncoskey.baba.dsl.decmods._
import net.emersoncoskey.baba.registry.item.ItemDec

object TestModule extends Baba.Module[Item %: Block %: DNil] {
	protected lazy val declarations: Item %: Block %: DNil = Seq(Amongus) %: Seq(AmongusBlock) %: DNil

	// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	val AmongusBlock: BlockDec[Block] = BlockDec("amongus_block", new TestBlock(_), BlockBehaviour.Properties.of(Material.STONE))(
		block.tags(Tags.Blocks.ORES_DIAMOND, Tags.Blocks.BARRELS, Tags.Blocks.GLASS, Tags.Blocks.COBBLESTONE)
	)

	val Amongus: ItemDec[BlockItem] = ItemDec("amongus", new BlockItem(Baba(AmongusBlock), _), new Item.Properties().tab(TestCreativeTab))(
		item.recipes(
			BabaShapelessRecipe(_, 5, Some("cringe_recipe"))(for {
				_ <- BabaShapelessRecipe.ingredients(Items.DIRT.i -> 4)
				_ <- BabaShapelessRecipe.group("among")
				_ <- BabaShapelessRecipe.unlockedByItem(Items.DIRT)
			} yield ()),

			BabaShapedRecipe(_, 64, Some("better_recipe"))(for {
				a <- BabaShapedRecipe.define('X', Items.DIAMOND.i)
				b <- BabaShapedRecipe.define('/', Items.DIRT.i)
				_ <- BabaShapedRecipe.pattern(List(b, a, b), List(a, a, a), List(b, a, b))
				_ <- BabaShapedRecipe.group("among")
				_ <- BabaShapedRecipe.unlockedBy("has_diamonds_and_dirt", BabaRecipe.has(Items.DIAMOND, Items.DIRT))
			} yield ()),

			BabaFurnaceRecipe.smelting(Items.DIAMOND.i, _, 1000000f, 100)(for {
				_ <- BabaFurnaceRecipe.group("among")
				_ <- BabaFurnaceRecipe.unlockedByItem(Items.DIAMOND)
			} yield ()
			),

			BabaShapedRecipe.packing3x3(Items.DIRT)
		),
		item.tags(Tags.Items.ORES_DIAMOND)
	)
}
