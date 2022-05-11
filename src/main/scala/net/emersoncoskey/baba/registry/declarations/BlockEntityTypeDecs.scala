package net.emersoncoskey.baba.registry.declarations

import net.emersoncoskey.baba.registry._
import net.minecraft.world.level.block.entity.{BlockEntity, BlockEntityType}
import net.minecraftforge.registries.RegistryObject

trait BlockEntityTypeDecs {
	def blockEntityType[E <: BlockEntity](name: String, builder: BlockEntityType.Builder[E]): McAction[RegistryObject[BlockEntityType[E]]] =
		declare[BlockEntityType[_], BlockEntityType[E]](name, builder.build(null))
}
