package net.emersoncoskey.cardboard

import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.apache.logging.log4j.{LogManager, Logger}

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Cardboard._modId)
object Cardboard extends CbMod {
	final val _modId = "cardboard"

	lazy val EventBus: IEventBus     = FMLJavaModLoadingContext.get.getModEventBus
	lazy val Logger  : Logger        = LogManager.getLogger
	lazy val Modules : Seq[CbModule] = TestModule :: Nil
}
