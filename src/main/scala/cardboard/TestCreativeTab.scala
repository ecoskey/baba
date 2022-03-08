package cardboard

import net.minecraft.world.item.{CreativeModeTab, ItemStack}

object TestCreativeTab extends CreativeModeTab("cardboard_items") {
	override def makeIcon: ItemStack = new ItemStack(TestModule.Amongus.get)
}