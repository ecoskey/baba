package foam

import net.minecraft.world.item.{CreativeModeTab, ItemStack}

object TestCreativeTab extends CreativeModeTab("foam_items") {
	def makeIcon: ItemStack = new ItemStack(Foam(TestModule.Amongus))
}
