package baba.registry.painting

import baba.registry.RegDec
import baba.registry.painting.PaintingDec.PaintingType
import net.minecraft.world.entity.decoration.Motive

import java.util.function.Supplier

class PaintingDec private(val name: String, width: Int, height: Int) extends RegDec[PaintingType] {
	def supplier: Supplier[Motive] = () => new Motive(width, height)
}

object PaintingDec {
	type PaintingType = Motive
	def apply(name: String, width: Int, height: Int): PaintingDec = new PaintingDec(name, width, height)
}
