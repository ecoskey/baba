package baba.datagen.lang

import baba.BaseMod
import baba.util.Lang
import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.LanguageProvider


class BabaLanguageProvider(gen: DataGenerator, mod: BaseMod, lang: Lang) extends LanguageProvider(gen, mod.ModId, lang.str) {
	override def addTranslations(): Unit = ???
}

object BabaLanguageProvider {
}
