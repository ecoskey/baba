package baba.datagen.loottable

sealed trait LootContextType

object LootContextType {
	case object Empty extends LootContextType
	case object Chest extends LootContextType
	case object Command extends LootContextType
	case object Selector extends LootContextType
	case object Fishing extends LootContextType
	case object Entity extends LootContextType
	case object Gift extends LootContextType
	case object Barter extends LootContextType
	case object AdvancementReward extends LootContextType
	case object AdvancementEntity extends LootContextType
	case object Generic extends LootContextType
	case object Block extends LootContextType
}
