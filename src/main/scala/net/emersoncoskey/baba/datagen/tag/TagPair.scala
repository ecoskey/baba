package net.emersoncoskey.baba.datagen.tag

import net.minecraft.tags.TagKey
import net.minecraftforge.registries.IForgeRegistryEntry

case class TagPair[R <: IForgeRegistryEntry[R]](target: R, tags: List[TagKey[R]])
