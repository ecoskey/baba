package foam

import foam.datagen.recipe.FoamRecipe
import foam.datagen.recipe.craftingtable.{FoamShapedRecipe, FoamShapelessRecipe}
import foam.datagen.recipe.furnace.FoamFurnaceRecipe
import foam.data.{%:, DNil}
import foam.registry.block.BlockDec
import foam.registry.item.ItemDec
import net.minecraft.world.item.{BlockItem, Item, Items}
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraftforge.common.Tags
import foam.syntax.all._
import foam.dsl.mods._

object TestModule extends Foam.Module[Item %: Block %: DNil] {
	protected lazy val declarations: Item %: Block %: DNil = Seq(Amongus) %: Seq(AmongusBlock) %: DNil

	// ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

	val AmongusBlock: BlockDec[Block] = BlockDec("amongus_block", new TestBlock(_), BlockBehaviour.Properties.of(Material.STONE))(
		B.tags(Tags.Blocks.ORES_DIAMOND, Tags.Blocks.BARRELS, Tags.Blocks.GLASS, Tags.Blocks.COBBLESTONE)
	)

	val Amongus: ItemDec[BlockItem] = ItemDec("amongus", new BlockItem(Foam(AmongusBlock), _), new Item.Properties().tab(TestCreativeTab))(
		I.recipes(
			FoamShapelessRecipe(_, 5, Some("cringe_recipe"))(for {
				_ <- FoamShapelessRecipe.ingredients(Items.DIRT.i -> 4)
				_ <- FoamShapelessRecipe.group("among")
				_ <- FoamShapelessRecipe.unlockedByItem(Items.DIRT)
			} yield ()
			),

			FoamShapedRecipe(_, 64, Some("better_recipe"))(for {
				a <- FoamShapedRecipe.define('X', Items.DIAMOND.i)
				b <- FoamShapedRecipe.define('/', Items.DIRT.i)
				_ <- FoamShapedRecipe.pattern(List(b, a, b), List(a, a, a), List(b, a, b))
				_ <- FoamShapedRecipe.group("among")
				_ <- FoamShapedRecipe.unlockedBy("has_diamonds_and_dirt", FoamRecipe.has(Items.DIAMOND, Items.DIRT))
			} yield ()
			),

			FoamFurnaceRecipe.smelting(Items.DIAMOND.i, _, 1000000f, 100)(for {
				_ <- FoamFurnaceRecipe.group("among")
				_ <- FoamFurnaceRecipe.unlockedByItem(Items.DIAMOND)
			} yield ()
			),

			FoamShapedRecipe.packing3x3(Items.DIRT)
		),
		I.tags(Tags.Items.ORES_DIAMOND)
	)
}
