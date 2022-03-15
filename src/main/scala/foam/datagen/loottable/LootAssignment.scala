package foam.datagen.loottable

import net.minecraft.world.level.storage.loot.LootTable

case class LootAssignment[+A](target: A, loot: Seq[LootTable])
