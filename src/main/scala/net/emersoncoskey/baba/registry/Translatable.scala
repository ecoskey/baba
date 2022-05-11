package net.emersoncoskey.baba.registry

import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.Item
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.registries.IForgeRegistryEntry

class Translatable[R <: IForgeRegistryEntry[R]](val tpe: String) extends AnyVal

object Translatable {
	def apply[R <: IForgeRegistryEntry[R]](implicit translatable: Translatable[R]): Translatable[R] = translatable

	implicit val itemTranslatable: Translatable[Item] =
		new Translatable("item")

	implicit val blockTranslatable: Translatable[Block] =
		new Translatable("block")

	implicit val fluidTranslatable: Translatable[Fluid] =
		new Translatable("fluid")

	implicit val entityTranslatable: Translatable[EntityType[_]] =
		new Translatable("entity")

	implicit val enchantmentTranslatable: Translatable[Enchantment] =
		new Translatable("enchantment")
}
