package net.emersoncoskey.cardboard

import net.emersoncoskey.cardboard.registry.Reg.Ops
import net.emersoncoskey.cardboard.registry.block.CbBlock
import net.emersoncoskey.cardboard.registry.dsl.{DecMod, ForgeDecMod, ModDecMod}
import net.emersoncoskey.cardboard.registry.enchantment.CbEnchantment
import net.emersoncoskey.cardboard.registry.item.CbItem
import net.emersoncoskey.cardboard.registry.potion.CbPotion
import net.emersoncoskey.cardboard.registry.soundEvent.CbSoundEvent
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.item.{Item, Items}
import net.minecraft.world.level.block.Block
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.TickEvent
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent
import net.minecraftforge.registries.{DeferredRegister, ForgeRegistries, RegistryObject}
import org.apache.logging.log4j.Logger

/*object CbMod {
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
}*/

trait CbMod {
	/** note: used because a constant value is necessary when using it as the Mod() annotation parameter */
	protected val _modId: String

	final lazy val ModId: String = _modId

	val EventBus: IEventBus
	val Logger  : Logger
	val Modules : Seq[CbModule]

	/* [REGISTRY STUFF] ***************************************************************************************************************************************/

	private val itemReg : DeferredRegister[Item]  = DeferredRegister.create(ForgeRegistries.ITEMS, ModId)
	itemReg.register(EventBus)
	private val items: Map[CbItem[Item], RegistryObject[Item]] = Map(
		(for {
			m <- Modules
			i <- m.items
			reg = i.reg(this)
		} yield i -> itemReg.register(reg.name, reg.sup)): _*
	)
	final def apply(i: CbItem[Item]): RegistryObject[Item] =
		items.getOrElse(i, throw new IllegalArgumentException(s"CbItem with name ${ i.name } has not been registered"))


	private val blockReg: DeferredRegister[Block] = DeferredRegister.create(ForgeRegistries.BLOCKS, ModId)
	blockReg.register(EventBus)
	private val blocks: Map[CbBlock[Block], RegistryObject[Block]] = Map(
		(for {
			m <- Modules
			b <- m.blocks
			reg = b.reg(this)
		} yield b -> blockReg.register(reg.name, reg.sup)): _*
	)
	final def apply(b: CbBlock[Block]): RegistryObject[Block] =
		blocks.getOrElse(b, throw new IllegalArgumentException(s"CbBlock with name ${ b.name } has not been registered"))


	private val potionReg: DeferredRegister[Potion] = DeferredRegister.create(ForgeRegistries.POTIONS, ModId)
	potionReg.register(EventBus)
	private val potions: Map[CbPotion[Potion], RegistryObject[Potion]] = Map(
		(for {
			m <- Modules
			b <- m.potions
			reg = b.reg(this)
		} yield b -> potionReg.register(reg.name, reg.sup)): _*
	)
	final def apply(p: CbPotion[Potion]): RegistryObject[Potion] =
		potions.getOrElse(p, throw new IllegalArgumentException(s"CbPotion with name ${ p.name } has not been registered"))

	private val soundEventReg: DeferredRegister[SoundEvent] = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ModId)
	soundEventReg.register(EventBus)
	private val soundEvents: Map[CbSoundEvent[SoundEvent], RegistryObject[SoundEvent]] = Map(
		(for {
			m <- Modules
			b <- m.soundEvents
			reg = b.reg(this)
		} yield b -> soundEventReg.register(reg.name, reg.sup)): _*
	)
	final def apply(s: CbSoundEvent[SoundEvent]): RegistryObject[SoundEvent] =
		soundEvents.getOrElse(s, throw new IllegalArgumentException(s"CbSoundEvent with name ${ s.name } has not been registered"))

	private val enchantmentReg: DeferredRegister[Enchantment] = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ModId)
	enchantmentReg.register(EventBus)
	private val enchantments: Map[CbEnchantment[Enchantment], RegistryObject[Enchantment]] = Map(
		(for {
			m <- Modules
			b <- m.enchantments
			reg = b.reg(this)
		} yield b -> enchantmentReg.register(reg.name, reg.sup)): _*
	)
	final def apply(e: CbEnchantment[Enchantment]): RegistryObject[Enchantment] =
		enchantments.getOrElse(e, throw new IllegalArgumentException(s"CbEnchantment with name ${ e.name } has not been registered"))

	/* [EVENT BUS THINGS] *************************************************************************************************************************************/

	val itemMods: List[(RegistryObject[Item], List[DecMod[Item]])] = items.toList.map { case (cbItem, reg) => (reg, cbItem.reg(this).mods.toList) }
	itemMods.foreach { case (item, mods) => mods.foreach {
		case m: ForgeDecMod[Item] => m.busRegister(item.get, this)
		case m: ModDecMod[Item] => m.busRegister(item.get, EventBus, this)
	}}

	val blockMods: List[(RegistryObject[Block], List[DecMod[Block]])] = blocks.toList.map { case (cbBlock, reg) => (reg, cbBlock.reg(this).mods.toList) }
	blockMods.foreach { case (block, mods) => mods.foreach {
		case m: ForgeDecMod[Block] => m.busRegister(block.get, this)
		case m: ModDecMod[Block] => m.busRegister(block.get, EventBus, this)
	}}

	/* [UTIL METHODS] *****************************************************************************************************************************************/

	def modLoc(path: String): ResourceLocation = new ResourceLocation(ModId, path)

	def mcLoc(path: String): ResourceLocation = new ResourceLocation(path)
}



