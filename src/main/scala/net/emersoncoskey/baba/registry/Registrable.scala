package net.emersoncoskey.baba.registry

import net.emersoncoskey.baba.util.PaintingType
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.sounds.SoundEvent
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.decoration.Motive
import net.minecraft.world.item.Item
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.registries.{ForgeRegistries, ForgeRegistry, IForgeRegistryEntry, RegistryManager}

class Registrable[R <: IForgeRegistryEntry[R]] private(val key: ResourceKey[Registry[R]], val objClass: Class[R]) {
	def getActiveRegistry: ForgeRegistry[R] = RegistryManager.ACTIVE.getRegistry(key)
}

object Registrable {
	def apply[R <: IForgeRegistryEntry[R]](implicit registrable: Registrable[R]): Registrable[R] = registrable

	private implicit val itemRegistrable: Registrable[Item] =
		new Registrable(ForgeRegistries.Keys.ITEMS, classOf[Item])

	implicit val blockRegistrable: Registrable[Block] =
		new Registrable(ForgeRegistries.Keys.BLOCKS, classOf[Block])

	implicit val fluidRegistrable: Registrable[Fluid] =
		new Registrable(ForgeRegistries.Keys.FLUIDS, classOf[Fluid])

	implicit val enchantmentRegistrable: Registrable[Enchantment] =
		new Registrable(ForgeRegistries.Keys.ENCHANTMENTS, classOf[Enchantment])

	implicit val paintingRegistrable: Registrable[PaintingType] =
		new Registrable(ForgeRegistries.Keys.PAINTING_TYPES, classOf[Motive])

	implicit val potionRegistrable: Registrable[Potion] =
		new Registrable(ForgeRegistries.Keys.POTIONS, classOf[Potion])

	implicit val soundEventRegistration: Registrable[SoundEvent] =
		new Registrable(ForgeRegistries.Keys.SOUND_EVENTS, classOf[SoundEvent])

	implicit val entityTypeRegistrable: Registrable[EntityType[_]] =
		new Registrable(ForgeRegistries.Keys.ENTITY_TYPES, classOf[EntityType[_]])

	implicit val blockEntityRegistrable: Registrable[BlockEntityType[_]] =
		new Registrable(ForgeRegistries.Keys.BLOCK_ENTITY_TYPES, classOf[BlockEntityType[_]])

	implicit val recipeSerializerRegistrable: Registrable[RecipeSerializer[_]] =
		new Registrable(ForgeRegistries.Keys.RECIPE_SERIALIZERS, classOf[RecipeSerializer[_]])

}
