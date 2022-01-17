package net.emersoncoskey.cardboard

import net.emersoncoskey.cardboard.block.CardboardBlock
import net.emersoncoskey.cardboard.item.CardboardItem
import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.{BlockItem, Item}
import net.minecraft.world.level.block.Block
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.registries.{ForgeRegistries, RegistryObject}

abstract class CardboardModule(_mod: => CardboardMod) {
	final lazy val Mod: CardboardMod = _mod

	protected val Items: Seq[CardboardItem[Item]]
	protected val Blocks: Seq[CardboardBlock[Block/*, BlockItem*/]]

	lazy val itemsRegistry: Map[CardboardItem[Item], RegistryObject[Item]] = Map(
		Items.map(i =>
			i -> RegistryObject.of[Item, Item](new ResourceLocation(Mod.ModId, i.name), ForgeRegistries.ITEMS)
		):_*
	)

	lazy val blocksRegistry: Map[CardboardBlock[Block/*, BlockItem*/], RegistryObject[Block]] = Map(
		Blocks.map(b =>
			b -> RegistryObject.of[Block, Block](new ResourceLocation(Mod.ModId, b.name), ForgeRegistries.BLOCKS)
		):_*
	)

	def apply(item: CardboardItem[Item]): RegistryObject[Item] = itemsRegistry(item)
	def apply(block: CardboardBlock[Block]): RegistryObject[Block] = blocksRegistry(block)

	@SubscribeEvent final def registerItems(event: RegistryEvent.Register[Item]): Unit = {
		Cardboard.Logger.info("RECIEVED REGISTRY EVENT")
		val itemSuppliers = Items.map(i => i.item().setRegistryName(new ResourceLocation(Mod.ModId, i.name)))
		event.getRegistry.registerAll(itemSuppliers:_*)
	}

	@SubscribeEvent final def registerBlocks(event: RegistryEvent.Register[Block]): Unit = {
		Cardboard.Logger.info("RECIEVED BLOCK REGISTRY EVENT")
		val blockSuppliers = Blocks.map(b => b.block().setRegistryName(new ResourceLocation(Mod.ModId, b.name)))
		event.getRegistry.registerAll(blockSuppliers:_*)
	}

	@SubscribeEvent final def clientSetup(event: FMLClientSetupEvent): Unit =
		Blocks.foreach(b => ItemBlockRenderTypes.setRenderLayer(this(b).get, b.renderType))
}