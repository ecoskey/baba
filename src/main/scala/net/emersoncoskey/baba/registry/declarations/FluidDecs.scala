package net.emersoncoskey.baba.registry.declarations

import net.emersoncoskey.baba.registry.{DecMod, McAction, declareWithMods}
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.registries.RegistryObject

trait FluidDecs {
	def fluid[F <: Fluid](name: String, fluid: => F, mods: DecMod[Fluid, F]*): McAction[RegistryObject[F]] =
		declareWithMods(name, fluid, mods:_*)
}
