package net.emersoncoskey.baba.registry.fluid

import net.emersoncoskey.baba.registry.RegDec
import net.minecraft.world.level.material.Fluid

import java.util.function.Supplier

/** Used to declare a [[Fluid]] to be added to the game */
class FluidDec[+F <: Fluid] private(val name: String, fluid: => F) extends RegDec[Fluid] {
	lazy val supplier: Supplier[Fluid] = () => fluid
}

object FluidDec {
	def apply[F <: Fluid](name: String, fluid: => F): FluidDec[F] = new FluidDec[F](name, fluid)
}
