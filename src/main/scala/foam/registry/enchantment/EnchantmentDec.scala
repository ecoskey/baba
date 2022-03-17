package foam.registry.enchantment

import foam.registry.{RegDec}
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.enchantment.{Enchantment, EnchantmentCategory}

import java.util.function.Supplier

case class EnchantmentDec[+E <: Enchantment](
	name    : String,
	ctor    : (Enchantment.Rarity, EnchantmentCategory, Array[EquipmentSlot]) => E,
	rarity  : Enchantment.Rarity,
	category: EnchantmentCategory,
	slots   : Seq[EquipmentSlot],
) extends RegDec[Enchantment] {
	lazy val supplier: Supplier[Enchantment] = () => ctor(rarity, category, slots.toArray)
}