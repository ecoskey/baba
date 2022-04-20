package net.emersoncoskey.baba.registry.declarations

import net.emersoncoskey.baba.registry.{Register, declare}
import net.emersoncoskey.baba.util.PaintingType
import net.minecraftforge.registries.RegistryObject


trait PaintingDeclarations {
	def painting(name: String, width: Int, height: Int): Register[RegistryObject[PaintingType]] =
		declare(name, new PaintingType(width, height))
}
