package net.emersoncoskey.cardboard.registry.enchantment

import net.emersoncoskey.cardboard.CbMod
import net.emersoncoskey.cardboard.registry.{Reg, RegistryDec}
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.enchantment.{Enchantment, EnchantmentCategory}
import net.minecraftforge.registries.{ForgeRegistries, IForgeRegistry}

case class CbEnchantment[E <: Enchantment](
	name    : String,
	ctor    : (Enchantment.Rarity, EnchantmentCategory, Seq[EquipmentSlot]) => E,
	rarity  : Enchantment.Rarity,
	category: EnchantmentCategory,
	slots   : Seq[EquipmentSlot],
)

object CbEnchantment {
	implicit val regInstance: Reg[CbEnchantment, Enchantment] = new Reg[CbEnchantment, Enchantment] {
		override val registry: IForgeRegistry[Enchantment] = ForgeRegistries.ENCHANTMENTS

		override def reg(r: CbEnchantment[Enchantment], mod: CbMod): RegistryDec[Enchantment] =
			RegistryDec(r.name, () => r.ctor(r.rarity, r.category, r.slots.toArray))
	}
}