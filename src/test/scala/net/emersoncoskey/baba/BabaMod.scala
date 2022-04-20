package net.emersoncoskey.baba

import net.emersoncoskey.baba.block.TestBlock
import net.emersoncoskey.baba.registry.{block, blockItem, _}
import net.minecraftforge.fml.common.Mod
import cats.syntax.all._
import net.minecraft.world.item.{BlockItem, CreativeModeTab, Item}
import net.minecraft.world.level.block.Block
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.registries.{ObjectHolder, RegistryObject}
import org.apache.logging.log4j.LogManager



/** An example implementation of BaseMod, and the entrypoint for this mod. */
@Mod(BabaMod.ModId)
@EventBusSubscriber(modid = BabaMod.ModId, bus = Bus.MOD)
object BabaMod {
	final val ModId = "baba"
	val Logger = LogManager.getLogger

	val Amongus: Register[(RegistryObject[TestBlock], RegistryObject[BlockItem])] =
		block("amongus_block", new TestBlock(_)) >>= blockItem("amongus", new Item.Properties().tab(CreativeModeTab.TAB_FOOD))

	val Cheese : Register[(RegistryObject[Block], RegistryObject[BlockItem])] =
		block("cheeseblock") >>= blockItem("cheese", new Item.Properties().tab(CreativeModeTab.TAB_FOOD))

	val baba: Baba = Baba(ModId, Amongus >> Cheese)

	val (amongusBlock, amongusItem) = baba(Amongus)
}
