package foam.registry.fluid

import foam.registry.RegDec
import net.minecraft.world.level.material.Fluid

import java.util.function.Supplier

/** Used to declare a [[Fluid]] to be added to the game */
class FluidDec[+F <: Fluid] private(val name: String, getter: => F) extends RegDec[Fluid] {
	lazy val supplier: Supplier[Fluid] = () => getter
}

object FluidDec {
	def apply[F <: Fluid](name: String, getter: => F): FluidDec[F] = new FluidDec[F](name, getter)
}
