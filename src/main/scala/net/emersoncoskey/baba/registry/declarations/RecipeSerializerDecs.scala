package net.emersoncoskey.baba.registry.declarations

import net.emersoncoskey.baba.registry.{Register, declare}
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraftforge.registries.RegistryObject

trait RecipeSerializerDecs {
	def recipeSerializer[S <: RecipeSerializer[_]](name: String, serializer: => S): Register[RegistryObject[S]] =
		declare[RecipeSerializer[_], S](name, serializer)
}
