package net.emersoncoskey.cardboard

import net.emersoncoskey.cardboard.block.CardboardBlock
import net.emersoncoskey.cardboard.item.CardboardItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.{DeferredRegister, ForgeRegistries, RegistryObject}
import org.apache.logging.log4j.Logger

abstract class CardboardMod {
	protected val _modId: String

	final def ModId: String = _modId

	val EventBus: IEventBus
	val Logger  : Logger
	val Modules : Seq[CardboardModule]

	private val itemReg: DeferredRegister[Item] = DeferredRegister.create(ForgeRegistries.ITEMS, ModId)
	private val blockReg: DeferredRegister[Block] = DeferredRegister.create(ForgeRegistries.BLOCKS, ModId)

	itemReg.register(EventBus)
	blockReg.register(EventBus)

	private val items: Map[CardboardItem[Item], RegistryObject[Item]] = Map(
		(for {
			m <- Modules
			i <- m.Items
		} yield i -> itemReg.register(i.name, () => i.item())):_*
	)


	private val blocks: Map[CardboardBlock[Block], RegistryObject[Block]] = Map(
		(for {
			m <- Modules
			b <- m.Blocks
		} yield b -> blockReg.register(b.name, () => b.block())):_*
	)

	def apply(i: CardboardItem[Item]): RegistryObject[Item] = items(i)
	def apply(b: CardboardBlock[Block]): RegistryObject[Block] = blocks(b)
}

