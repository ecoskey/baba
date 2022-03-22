package net.emersoncoskey.baba.datagen.loottable

sealed trait LootFn

object LootFn {
	case class Bonus() extends LootFn

	case class CopyName() extends LootFn

	case class CopyNbt() extends LootFn

	case class CopyState() extends LootFn

	case class EnchantRandomly() extends LootFn

	case class EnchantWithLevels() extends LootFn

	case class ExplorationMap() extends LootFn

	case class ExplosionDecay() extends LootFn

	case class FurnaceSmelt() extends LootFn

	case class FillPlayerHead() extends LootFn

	case class LimitCount() extends LootFn

	case class LootingEnchant() extends LootFn

	case class SetAttributes() extends LootFn

	case class SetBannerPattern() extends LootFn

	case class SetContents() extends LootFn

	case class SetCount() extends LootFn

	case class SetDamage() extends LootFn

	case class SetEnchantments() extends LootFn

	case class SetLootTable() extends LootFn

	case class SetLore() extends LootFn

	case class SetName() extends LootFn

	case class SetNbt() extends LootFn

	case class SetPotion() extends LootFn

	case class SetStewEffect() extends LootFn
}
