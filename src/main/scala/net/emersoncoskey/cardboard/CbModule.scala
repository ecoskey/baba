package net.emersoncoskey.cardboard

import net.emersoncoskey.cardboard.block.CbBlock
import net.emersoncoskey.cardboard.item.CbItem
import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.{BlockItem, Item}
import net.minecraft.world.level.block.Block
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.registries.{DeferredRegister, ForgeRegistries, RegistryObject}

trait CbModule {
	val items : Seq[CbItem[Item]]
	val blocks: Seq[CbBlock[Block/*, BlockItem*/]]

/*	lazy val itemsMap: Map[CardboardItem[Item], RegistryObject[Item]] = Map(
		Items.map(i =>
			i -> RegistryObject.of[Item, Item](new ResourceLocation)
		):_*
	)

	lazy val blocksMap: Map[CardboardBlock[Block/*, BlockItem*/], RegistryObject[Block]] = Map(
		Blocks.map(b =>
			b -> blocksReg.register(b.name, () => b.block())
		):_*
	)

	final def apply(item: CardboardItem[Item]): RegistryObject[Item] = itemsMap(item)
	final def apply(block: CardboardBlock[Block]): RegistryObject[Block] = blocksMap(block)*/

	/*@SubscribeEvent final def registerItems(event: RegistryEvent.Register[Item]): Unit = {
		Cardboard.Logger.info("RECIEVED REGISTRY EVENT")
		val itemSuppliers = items.map(i => i.item().setRegistryName(new ResourceLocation(Mod.ModId, i.name)))
		event.getRegistry.registerAll(itemSuppliers:_*)
	}

	@SubscribeEvent final def registerBlocks(event: RegistryEvent.Register[Block]): Unit = {
		Cardboard.Logger.info("RECIEVED BLOCK REGISTRY EVENT")
		val blockSuppliers = blocks.map(b => b.block().setRegistryName(new ResourceLocation(Mod.ModId, b.name)))
		event.getRegistry.registerAll(blockSuppliers:_*)
	}*/

	/*@SubscribeEvent final def clientSetup(event: FMLClientSetupEvent): Unit =
		blocks.foreach(b => ItemBlockRenderTypes.setRenderLayer(this(b).get, b.renderType))*/
}