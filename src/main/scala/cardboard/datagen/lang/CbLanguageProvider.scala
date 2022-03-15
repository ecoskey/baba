package cardboard.datagen.lang

import cardboard.CbMod
import cardboard.util.Lang
import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.LanguageProvider


class CbLanguageProvider(gen: DataGenerator, mod: CbMod, lang: Lang) extends LanguageProvider(gen, mod.ModId, lang.str) {
	override def addTranslations(): Unit = ???
}

object CbLanguageProvider {
	sealed trait
}
