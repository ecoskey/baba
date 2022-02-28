package cardboard

import cardboard.registry.block.CbBlock
import cardboard.registry.enchantment.CbEnchantment
import cardboard.registry.item.CbItem
import cardboard.registry.potion.CbPotion
import cardboard.registry.soundEvent.CbSoundEvent
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.item.Item
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.block.Block

trait CbModule {
	val items : Seq[CbItem[Item]]
	val blocks: Seq[CbBlock[Block]]
	val potions: Seq[CbPotion[Potion]]
	val soundEvents: Seq[CbSoundEvent[SoundEvent]]
	val enchantments: Seq[CbEnchantment[Enchantment]]
}