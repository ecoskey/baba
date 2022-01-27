package net.emersoncoskey.cardboard

import net.emersoncoskey.cardboard.block.CbBlock
import net.emersoncoskey.cardboard.item.CbItem
import net.emersoncoskey.cardboard.recipe.{CbRecipe, CbRecipeProvider}
import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.eventbus.api.{IEventBus, SubscribeEvent}
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent
import net.minecraftforge.registries.{DeferredRegister, ForgeRegistries, RegistryObject}
import org.apache.logging.log4j.Logger


abstract class CbMod {
	protected val _modId: String

	final def ModId: String = _modId

	implicit val mod: CbMod = this

	val EventBus: IEventBus
	val Logger  : Logger
	val Modules : Seq[CbModule]

	/* [REGISTRY STUFF] ----------------------------------------------------------------------------------------------*/

	private val itemReg : DeferredRegister[Item]  = DeferredRegister.create(ForgeRegistries.ITEMS, ModId)
	private val blockReg: DeferredRegister[Block] = DeferredRegister.create(ForgeRegistries.BLOCKS, ModId)

	itemReg.register(EventBus)
	blockReg.register(EventBus)

	private val items: Map[CbItem[Item], RegistryObject[Item]] = Map(
		(for {
			m <- Modules
			i <- m.items
		} yield i -> itemReg.register(i.name, () => i.item())): _*
	)

	private val blocks: Map[CbBlock[Block], RegistryObject[Block]] = Map(
		(for {
			m <- Modules
			b <- m.blocks
		} yield b -> blockReg.register(b.name, () => b.block())): _*
	)

	final def apply(i: CbItem[Item]): RegistryObject[Item] = items(i)

	final def apply(b: CbBlock[Block/*, BlockItem*/]): RegistryObject[Block] = blocks(b)

	/* [EVENT BUS THINGS] --------------------------------------------------------------------------------------------*/

	EventBus.register(this)

	@SubscribeEvent final def gatherData(event: GatherDataEvent): Unit = {
		val generator = event.getGenerator

		val recipes: List[CbRecipe] = for {
			(i, reg) <- items.toList
			r <- i.recipes
		} yield r(reg.get)

		generator.addProvider(CbRecipeProvider(generator, recipes:_*))
	}

	@SubscribeEvent final def commonSetup(event: FMLCommonSetupEvent): Unit =
		blocks.foreach { case (b, reg) => ItemBlockRenderTypes.setRenderLayer(reg.get, b.renderType) }

}

