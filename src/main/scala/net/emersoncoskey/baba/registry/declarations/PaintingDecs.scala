package net.emersoncoskey.baba.registry.declarations

import net.emersoncoskey.baba.registry.{McAction, declare}
import net.emersoncoskey.baba.util.PaintingType
import net.minecraftforge.registries.RegistryObject


trait PaintingDecs {
	def painting(name: String, width: Int, height: Int): McAction[RegistryObject[PaintingType]] =
		declare(name, new PaintingType(width, height))
}
