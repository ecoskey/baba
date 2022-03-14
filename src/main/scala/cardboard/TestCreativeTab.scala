package cardboard

import net.minecraft.world.item.{CreativeModeTab, ItemStack}

object TestCreativeTab extends CreativeModeTab("cardboard_items") {
	def makeIcon: ItemStack = new ItemStack(Cardboard(TestModule.Amongus))
}