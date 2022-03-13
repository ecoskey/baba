package cardboard

import net.minecraft.world.item.{CreativeModeTab, ItemStack, Items}

object TestCreativeTab extends CreativeModeTab("cardboard_items") {
	def makeIcon: ItemStack = new ItemStack(Items.DIRT)
}