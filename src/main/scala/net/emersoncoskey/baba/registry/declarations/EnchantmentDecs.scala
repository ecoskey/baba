package net.emersoncoskey.baba.registry.declarations

import net.emersoncoskey.baba.registry._
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.enchantment.{Enchantment, EnchantmentCategory}
import net.minecraftforge.registries.RegistryObject

trait EnchantmentDecs {
	def enchantment[E <: Enchantment](
		name    : String,
		ctor    : (Enchantment.Rarity, EnchantmentCategory, Array[EquipmentSlot]) => E,
		rarity  : Enchantment.Rarity,
		category: EnchantmentCategory,
		slots   : List[EquipmentSlot]
	): McAction[RegistryObject[E]] = declare[Enchantment, E](name, ctor(rarity, category, slots.toArray))
}
