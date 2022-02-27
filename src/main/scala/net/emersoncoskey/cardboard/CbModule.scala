package net.emersoncoskey.cardboard

import net.emersoncoskey.cardboard.registry.block.CbBlock
import net.emersoncoskey.cardboard.registry.enchantment.CbEnchantment
import net.emersoncoskey.cardboard.registry.item.CbItem
import net.emersoncoskey.cardboard.registry.potion.CbPotion
import net.emersoncoskey.cardboard.registry.soundEvent.CbSoundEvent
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