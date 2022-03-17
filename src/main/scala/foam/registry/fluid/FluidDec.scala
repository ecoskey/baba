package foam.registry.fluid

import foam.registry.RegDec
import net.minecraft.world.level.material.Fluid

import java.util.function.Supplier

case class FluidDec[+F <: Fluid](name: String, ctor: () => F) extends RegDec[Fluid] {
	lazy val supplier: Supplier[Fluid] = () => ctor()
}
