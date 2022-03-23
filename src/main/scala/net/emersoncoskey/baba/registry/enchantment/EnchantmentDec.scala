package net.emersoncoskey.baba.registry.enchantment

import net.emersoncoskey.baba.registry.RegDec
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.enchantment.{Enchantment, EnchantmentCategory}

import java.util.function.Supplier

/** Used to declare an [[Enchantment]] to be added to the game */
class EnchantmentDec[+E <: Enchantment] private(
	val name: String,
	ctor    : (Enchantment.Rarity, EnchantmentCategory, Array[EquipmentSlot]) => E,
	rarity  : Enchantment.Rarity,
	category: EnchantmentCategory,
	slots   : Seq[EquipmentSlot],
) extends RegDec[Enchantment] {
	lazy val supplier: Supplier[Enchantment] = () => ctor(rarity, category, slots.toArray)
}

object EnchantmentDec {
	def apply[E <: Enchantment](
		name    : String,
		ctor: (Enchantment.Rarity, EnchantmentCategory, Array[EquipmentSlot]) => E,
		rarity  : Enchantment.Rarity,
		category: EnchantmentCategory,
		slots   : Seq[EquipmentSlot]
	): EnchantmentDec[E] = new EnchantmentDec[E](name, ctor, rarity, category, slots)
}