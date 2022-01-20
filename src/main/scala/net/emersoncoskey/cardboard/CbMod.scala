package net.emersoncoskey.cardboard

import net.emersoncoskey.cardboard.block.CbBlock
import net.emersoncoskey.cardboard.item.CbItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.eventbus.api.{IEventBus, SubscribeEvent}
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent
import net.minecraftforge.registries.{DeferredRegister, ForgeRegistries, RegistryObject}
import org.apache.logging.log4j.Logger

abstract class CbMod {
	protected val _modId: String

	final def ModId: String = _modId

	val EventBus: IEventBus
	val Logger  : Logger
	val Modules : Seq[CbModule]

	private val itemReg : DeferredRegister[Item]  = DeferredRegister.create(ForgeRegistries.ITEMS, ModId)
	private val blockReg: DeferredRegister[Block] = DeferredRegister.create(ForgeRegistries.BLOCKS, ModId)

	itemReg.register(EventBus)
	blockReg.register(EventBus)

	private val items: Map[CbItem[Item], RegistryObject[Item]] = Map(
		(for {
			m <- Modules
			i <- m.Items
		} yield i -> itemReg.register(i.name, () => i.item())): _*
	)

	private val blocks: Map[CbBlock[Block], RegistryObject[Block]] = Map(
		(for {
			m <- Modules
			b <- m.Blocks
		} yield b -> blockReg.register(b.name, () => b.block())): _*
	)

	final def apply(i: CbItem[Item]): RegistryObject[Item] = items(i)

	final def apply(b: CbBlock[Block]): RegistryObject[Block] = blocks(b)

	EventBus.register(this)

	@SubscribeEvent final def gatherData(event: GatherDataEvent): Unit = {
		val generator = event.getGenerator

		//models, recipes, etc...
	}
}

