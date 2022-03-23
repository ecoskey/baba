package net.emersoncoskey.baba.registry.painting

import net.emersoncoskey.baba.registry.RegDec
import net.emersoncoskey.baba.util.PaintingType
import net.minecraft.world.entity.decoration.Motive

import java.util.function.Supplier

class PaintingDec private(val name: String, width: Int, height: Int) extends RegDec[PaintingType] {
	def supplier: Supplier[Motive] = () => new Motive(width, height)
}

object PaintingDec {
	def apply(name: String, width: Int, height: Int): PaintingDec = new PaintingDec(name, width, height)
}
