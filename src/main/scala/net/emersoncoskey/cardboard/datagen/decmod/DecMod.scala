package net.emersoncoskey.cardboard.datagen.decmod

import net.emersoncoskey.cardboard.CbMod
import net.minecraft.data.{DataGenerator, DataProvider}
import net.minecraftforge.registries.IForgeRegistryEntry

/*trait DecMod[R <: IForgeRegistryEntry[R]] extends ((CbMod, DataGenerator, R) => DataProvider) {
	/** Ex: "item model", "tags", "block states", etc. */
	val name: String
}*/
sealed trait DecMod[-A]

