package net.emersoncoskey.baba.registry.declarations

import net.emersoncoskey.baba.registry._
import net.minecraft.world.entity.{Entity, EntityType}
import net.minecraftforge.registries.RegistryObject

trait EntityTypeDecs {
	def entityType[E <: Entity](name: String, builder: EntityType.Builder[E]): McAction[RegistryObject[EntityType[E]]] =
		declare[EntityType[_], EntityType[E]](name, builder.build(name))
}
