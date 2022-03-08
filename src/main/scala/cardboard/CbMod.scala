package cardboard

import cardboard.data.hlistops._
import cardboard.data.{::, HNil}
import cardboard.registry.{CbRegistry, Reg}
import cardboard.syntax.all._
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.item.Item
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.block.Block
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.ForgeRegistries
import org.apache.logging.log4j.Logger

trait CbMod {
	/** note: used because a constant value is necessary when using it as the Mod() annotation parameter */
	protected val _modId: String

	final lazy val ModId: String = _modId

	val eventBus: IEventBus
	val logger  : Logger
	val modules : Seq[CbModule]

	/* [REGISTRY STUFF] ***************************************************************************************************************************************/

	type DefaultRegistries = Item :: Block :: Enchantment :: Potion :: SoundEvent :: HNil
	private val DefaultRegistries: DefaultRegistries =
		new CbRegistry[Item](this, ForgeRegistries.ITEMS)               ::
		new CbRegistry[Block](this, ForgeRegistries.BLOCKS)             ::
		new CbRegistry[Enchantment](this, ForgeRegistries.ENCHANTMENTS) ::
		new CbRegistry[Potion](this, ForgeRegistries.POTIONS)           ::
		new CbRegistry[SoundEvent](this, ForgeRegistries.SOUND_EVENTS)  ::
		HNil

	private val totalRegistries = DefaultRegistries //todo: custom registry support

	def get(dec: Reg)(implicit in: dec.A in DefaultRegistries): dec.A = in.get(totalRegistries).get(dec.reg).get

	def apply(dec: Reg)(implicit in: dec.A in DefaultRegistries): dec.A = get(dec)

	/* [EVENT BUS STUFF] */

	/* [UTIL METHODS] *****************************************************************************************************************************************/

	def modLoc(path: String): ResourceLocation = new ResourceLocation(ModId, path)

	def mcLoc(path: String): ResourceLocation = new ResourceLocation(path)
}