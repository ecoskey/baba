package cardboard

import cardboard.datagen.recipe.CbRecipe
import cardboard.datagen.recipe.craftingtable.{CbShapedRecipe, CbShapelessRecipe}
import cardboard.datagen.recipe.furnace.CbFurnaceRecipe
import cardboard.registry.block.BlockDec
import cardboard.registry.item.ItemDec
import net.minecraft.world.item.{BlockItem, Item, Items}
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraftforge.common.Tags
import cardboard.Cardboard.defaultRegs._
import cardboard.syntax.all._
import cardboard.dsl.mods._
import cardboard.registry.Reg

object TestModule extends CbModule {
	lazy val registryDecs: Seq[Reg[_]] = Seq(Amongus, AmongusBlock)

	// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	val AmongusBlock: BlockDec[Block] = BlockDec("amongus_block", new TestBlock(_), BlockBehaviour.Properties.of(Material.STONE))(
		B.tags(Tags.Blocks.ORES_DIAMOND, Tags.Blocks.BARRELS, Tags.Blocks.GLASS, Tags.Blocks.COBBLESTONE)
	)

	val Amongus: ItemDec[BlockItem] = ItemDec("amongus", new BlockItem(AmongusBlock.get, _), new Item.Properties().tab(TestCreativeTab))(
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
		),
		I.tags(Tags.Items.ORES_DIAMOND)
	)
}
