package foam.datagen.lang

import foam.BaseMod
import foam.util.Lang
import net.minecraft.data.DataGenerator
import net.minecraftforge.common.data.LanguageProvider


class FoamLanguageProvider(gen: DataGenerator, mod: BaseMod, lang: Lang) extends LanguageProvider(gen, mod.ModId, lang.str) {
	override def addTranslations(): Unit = ???
}

object FoamLanguageProvider {
}
