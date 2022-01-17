package net.emersoncoskey.cardboard

import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.apache.logging.log4j.{LogManager, Logger}

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Cardboard._modId)
object Cardboard extends CardboardMod {
	final val _modId = "cardboard"

	lazy val EventBus: IEventBus            = FMLJavaModLoadingContext.get.getModEventBus
	lazy val Logger  : Logger               = LogManager.getLogger
	lazy val Modules : Seq[CardboardModule] = Seq(TestModule)

	// Register the setup method for modloading
	/*FMLJavaModLoadingContext.get.getModEventBus.addListener(this.setup)
	// Register the enqueueIMC method for modloading
	FMLJavaModLoadingContext.get.getModEventBus.addListener(this.enqueueIMC)
	// Register the processIMC method for modloading
	FMLJavaModLoadingContext.get.getModEventBus.addListener(this.processIMC)
	// Register the doClientStuff method for modloading
	FMLJavaModLoadingContext.get.getModEventBus.addListener(this.doClientStuff)

	// Register ourselves for server and other game events we are interested in
	MinecraftForge.EVENT_BUS.register(this)

	private def setup(event: FMLCommonSetupEvent): Unit = {
		// some preinit code
		LOGGER.info("HELLO FROM PRE-INIT")
		LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName)
	}

	private def doClientStuff(event: FMLClientSetupEvent): Unit = {
		// do something that can only be done on the client
		LOGGER.info("Hello Client!")
	}

	private def enqueueIMC(event: InterModEnqueueEvent): Unit = {
		// some example code to dispatch IMC to another mod
		InterModComms.sendTo(
			ModId, "helloworld", () => {
				LOGGER.info("Hello world from the MDK")
				"Hello world"
			}
		)
	}

	private def processIMC(event: InterModProcessEvent): Unit = {
		// some example code to receive and process InterModComms from other mods
		import scala.jdk.StreamConverters._
		val messages = event.getIMCStream.toScala(LazyList).map(_.messageSupplier().get())
		LOGGER.info("Got IMC {}", messages.mkString(", "))
	}

	// You can use SubscribeEvent and let the Event Bus discover methods to call
	@SubscribeEvent
	def onServerStarting(event: ServerStartingEvent): Unit = {
		// do something when the server starts
		LOGGER.info("HELLO from server starting")
	}*/

}

// You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
// Event bus for receiving Registry Events)
// The object must be at the top-level. Don't forget to fill modid.
/*@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Cardboard.ModId)
object RegistryEvents {
	private val LOGGER = LogManager.getLogger

	@SubscribeEvent
	def onBlocksRegistry(blockRegistryEvent: RegistryEvent.Register[Block]): Unit = {
		// register a new block here
		LOGGER.info("HELLO from Register Block")
	}
}*/
