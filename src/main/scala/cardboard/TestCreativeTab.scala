package cardboard

import net.minecraft.world.item.{CreativeModeTab, ItemStack, Items}

object TestCreativeTab extends CreativeModeTab("cardboard_items") {
	override def makeIcon: ItemStack = new ItemStack(Cardboard(TestModule.Amongus).get)
}