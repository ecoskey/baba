package foam.datagen.lang

import cardboard.CbMod
import foam.util.Lang
import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.LanguageProvider


class FoamLanguageProvider(gen: DataGenerator, mod: CbMod, lang: Lang) extends LanguageProvider(gen, mod.ModId, lang.str) {
	override def addTranslations(): Unit = ???
}

object FoamLanguageProvider {
	sealed trait
}
