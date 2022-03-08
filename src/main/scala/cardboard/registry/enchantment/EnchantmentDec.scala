package cardboard.registry.enchantment

import cardboard.CbMod
import cardboard.registry.{Reg, RegistryDec}
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.enchantment.{Enchantment, EnchantmentCategory}
import net.minecraftforge.registries.{ForgeRegistries, IForgeRegistry}

case class EnchantmentDec[+E <: Enchantment](
	name    : String,
	ctor    : (Enchantment.Rarity, EnchantmentCategory, Array[EquipmentSlot]) => E,
	rarity  : Enchantment.Rarity,
	category: EnchantmentCategory,
	slots   : Seq[EquipmentSlot],
)

object EnchantmentDec {
	/*implicit val regInstance: Reg[EnchantmentDec, Enchantment] = new Reg[EnchantmentDec, Enchantment] {
		val registry: IForgeRegistry[Enchantment] = ForgeRegistries.ENCHANTMENTS

		def reg(r: EnchantmentDec[Enchantment], mod: CbMod): RegistryDec[Enchantment] =
			RegistryDec(r.name, () => r.ctor(r.rarity, r.category, r.slots.toArray))
	}*/
}