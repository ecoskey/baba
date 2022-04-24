package net.emersoncoskey.baba.datagen.lang

import net.minecraft.data.{DataGenerator, DataProvider, HashCache}

class BabaLangProvider(modId: String, gen: DataGenerator, mappings: Map[Lang, Map[String, String]]) extends DataProvider {
	def getName: String = "Baba Lang Provider"

	def run(pCache: HashCache): Unit =
}
