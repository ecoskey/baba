package net.emersoncoskey.cardboard

import net.emersoncoskey.cardboard.block.CardboardBlock
import net.emersoncoskey.cardboard.item.CardboardItem
import net.minecraft.world.item.{BlockItem, CreativeModeTab, Item}
import net.minecraft.world.level.block.Block
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus
import net.minecraftforge.registries.{DeferredRegister, RegistryObject}

import java.util.function.Supplier

abstract class Module(val Mod: CardboardMod) {
	val Items: Seq[CardboardItem[Item]] =
		Seq(CardboardItem.named("among").properties(new Item.Properties().tab(CreativeModeTab.TAB_MISC)).build)
	val Blocks: Seq[CardboardBlock[Block, BlockItem]]

	val ItemSuppliers: Seq[Item] = Items.map(i => i.item().setRegistryName(s"${Mod.ModId}:${i.name}"))

	def registerItems(event: RegistryEvent.Register[Item]): Unit = {
		event.getRegistry.registerAll(ItemSuppliers:_*)
	}
}