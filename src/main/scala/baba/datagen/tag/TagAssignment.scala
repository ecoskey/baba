package baba.datagen.tag

import net.minecraft.tags.TagKey

case class TagAssignment[A](obj: A, target: Seq[TagKey[A]])
