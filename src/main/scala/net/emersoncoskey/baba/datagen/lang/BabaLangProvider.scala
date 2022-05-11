package net.emersoncoskey.baba.datagen.lang

import net.emersoncoskey.baba.util.Lang
import net.minecraft.data.{DataGenerator, DataProvider, HashCache}

class BabaLangProvider(modId: String, gen: DataGenerator, mappings: List[(Lang, List[(String, String)])]) extends DataProvider {
	def getName: String = "Baba Lang Provider"
	private def pathOf(lang: Lang) = gen.getOutputFolder.resolve(s"assets/$modId/lang/${lang.str}.json")

	def run(cache: HashCache): Unit = ???
}
