package cardboard.datagen.loottable

import net.minecraft.data.loot.BlockLoot
import net.minecraft.world.level.block.Block

import java.lang
import scala.jdk.CollectionConverters._

class CbBlockLoot(assignments: Seq[LootAssignment[Block]]) extends BlockLoot {
	override def getKnownBlocks: lang.Iterable[Block] = assignments.map(_.target).asJava

	override def addTables(): Unit = super.addTables()
}
