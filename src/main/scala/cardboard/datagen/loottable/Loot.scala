package cardboard.datagen.loottable

case class Loot(
	lootType: LootContextType,
	functions: List[Any],
	pools: List[Any]
)
