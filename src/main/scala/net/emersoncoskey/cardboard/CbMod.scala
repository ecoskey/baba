package net.emersoncoskey.cardboard

import net.emersoncoskey.cardboard.registry.Reg.Ops
import net.emersoncoskey.cardboard.registry.block.CbBlock
import net.emersoncoskey.cardboard.registry.item.CbItem
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.eventbus.api.{Event, IEventBus}
import net.minecraftforge.registries.{DeferredRegister, ForgeRegistries, RegistryObject}
import org.apache.logging.log4j.Logger

object CbMod {
	trait EventListener {
		type E <: Event
		final def register(eventBus: IEventBus, mod: CbMod): Unit = eventBus.addListener[E](e => handleEvent(e, mod))
		def handleEvent(event: E, mod: CbMod): Unit
	}

	object EventListener {
		def apply[e <: Event](f: (e, CbMod) => Unit): EventListener = new EventListener {
			type E = e
			override def handleEvent(event: E, mod: CbMod): Unit = f(event, mod)
		}
	}
}

trait CbMod {
	/** note: used because a constant value is necessary when using it as the Mod() annotation parameter */
	protected val _modId: String

	final lazy val ModId: String = _modId

	val EventBus: IEventBus
	val Logger  : Logger
	val Modules: Seq[CbModule]
	
	/* [REGISTRY STUFF] ***************************************************************************************************************************************/

	private val itemReg : DeferredRegister[Item]  = DeferredRegister.create(ForgeRegistries.ITEMS, ModId)
	private val blockReg: DeferredRegister[Block] = DeferredRegister.create(ForgeRegistries.BLOCKS, ModId)

	itemReg.register(EventBus)
	blockReg.register(EventBus)

	private val items: Map[CbItem[Item], RegistryObject[Item]] = Map(
		(for {
			m <- Modules
			i <- m.items
			reg = i.reg
		} yield i -> itemReg.register(reg.name, reg.sup)): _*
	)

	private val blocks: Map[CbBlock[Block], RegistryObject[Block]] = Map(
		(for {
			m <- Modules
			b <- m.blocks
			reg = b.reg
		} yield b -> blockReg.register(reg.name, reg.sup)): _*
	)

	final def apply(i: CbItem[Item]): RegistryObject[Item] =
		items.getOrElse(i, throw new IllegalArgumentException(s"CbItem with name ${ i.name } has not been registered"))

	final def apply(b: CbBlock[Block]): RegistryObject[Block] =
		blocks.getOrElse(b, throw new IllegalArgumentException(s"CbBlock with name ${ b.name } has not been registered"))

	/* [EVENT BUS THINGS] *************************************************************************************************************************************/

	/*val itemMods: List[(Item, DecMod[Item, Unit])] = items.toList.map { case (cbItem, reg) => (reg.get, cbItem.reg.mods) }
	val blockMods: List[(Block, DecMod[Block, Unit])] = blocks.toList.map { case (cbBlock, reg) => (reg.get, cbBlock.reg.mods) }

	itemMods.foreach { case (item, mod) => mod.run(item)._1.map(_.register(EventBus, this))}
	blockMods.foreach { case (block, mod) => mod.run(block)._1.map(_.register(EventBus, this))}*/
	/*@SubscribeEvent final def gatherData(event: GatherDataEvent): Unit = {
		val generator = event.getGenerator
		val helper    = event.getExistingFileHelper

		val itemsList  = items.toList
		val blocksList = blocks.toList

		val itemData = itemsList.flatMap{ case (i, r) => i.mods.map(_(this, generator, r.get)) }
		itemData.foreach(generator.addProvider)

		/*val blockTags: List[(Block, List[Tag.Named[Block]])] = blocksList.map { case (b, reg) => reg.get -> b.tags }
		val blockTagsProvider                                = CbBlockTagsProvider(this, generator, helper, blockTags)
		generator.addProvider(blockTagsProvider)

		val itemTags: List[(Item, List[Tag.Named[Item]])] = itemsList.map { case (i, reg) => reg.get -> i.tags }
		generator.addProvider(CbItemTagsProvider(this, generator, blockTagsProvider, helper, itemTags))*/
	}*/

	/*@SubscribeEvent final def commonSetup(event: FMLCommonSetupEvent): Unit =
		blocks.foreach { case (b, reg) => ItemBlockRenderTypes.setRenderLayer(reg.get, b.renderType) }*/

	/* [UTIL METHODS] *****************************************************************************************************************************************/

	def modLoc(path: String): ResourceLocation = new ResourceLocation(ModId, path)

	def mcLoc(path: String): ResourceLocation = new ResourceLocation(path)
}



