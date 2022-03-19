package baba

import net.minecraft.world.item.{CreativeModeTab, ItemStack}

object TestCreativeTab extends CreativeModeTab("baba_items") {
	def makeIcon: ItemStack = new ItemStack(Baba(TestModule.Amongus))
}
