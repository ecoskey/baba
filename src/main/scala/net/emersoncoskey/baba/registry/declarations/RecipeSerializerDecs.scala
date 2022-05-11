package net.emersoncoskey.baba.registry.declarations

import net.emersoncoskey.baba.registry.{McAction, declare}
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraftforge.registries.RegistryObject

trait RecipeSerializerDecs {
	def recipeSerializer[S <: RecipeSerializer[_]](name: String, serializer: => S): McAction[RegistryObject[S]] =
		declare[RecipeSerializer[_], S](name, serializer)
}
