package net.emersoncoskey.baba.registry.declarations

import net.emersoncoskey.baba.registry.{Register, declare}
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.enchantment.{Enchantment, EnchantmentCategory}
import net.minecraftforge.registries.RegistryObject

trait EnchantmentDeclarations {
	def enchantment[E <: Enchantment](
		name    : String,
		ctor    : (Enchantment.Rarity, EnchantmentCategory, Array[EquipmentSlot]) => E,
		rarity  : Enchantment.Rarity,
		category: EnchantmentCategory,
		slots   : List[EquipmentSlot]
	): Register[RegistryObject[Enchantment]] = declare(name, ctor(rarity, category, slots.toArray))
}
