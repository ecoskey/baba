package net.emersoncoskey.baba.registry

import net.emersoncoskey.baba.util.PaintingType
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraft.world.entity.decoration.Motive
import net.minecraft.world.item.Item
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.material.Fluid
import net.minecraftforge.registries.{ForgeRegistries, ForgeRegistry, IForgeRegistryEntry, RegistryManager}

trait Registrable[R <: IForgeRegistryEntry[R]] {
	val key: ResourceKey[Registry[R]]
	val objClass: Class[R]

	def getActiveRegistry: ForgeRegistry[R] = RegistryManager.ACTIVE.getRegistry(key)
}

object Registrable {
	def apply[R <: IForgeRegistryEntry[R]](implicit registrable: Registrable[R]): Registrable[R] = registrable

	def create[R <: IForgeRegistryEntry[R]](_key: ResourceKey[Registry[R]], _objClass: Class[R]): Registrable[R] = new Registrable[R] {
		val key: ResourceKey[Registry[R]] = _key
		val objClass: Class[R] = _objClass
	}

	implicit val itemRegistrable       : Registrable[Item]         = Registrable.create(ForgeRegistries.Keys.ITEMS, classOf[Item])
	implicit val blockRegistrable      : Registrable[Block]        = Registrable.create(ForgeRegistries.Keys.BLOCKS, classOf[Block])
	implicit val fluidRegistrable      : Registrable[Fluid]        = Registrable.create(ForgeRegistries.Keys.FLUIDS, classOf[Fluid])
	implicit val enchantmentRegistrable: Registrable[Enchantment]  = Registrable.create(ForgeRegistries.Keys.ENCHANTMENTS, classOf[Enchantment])
	implicit val paintingRegistrable   : Registrable[PaintingType] = Registrable.create(ForgeRegistries.Keys.PAINTING_TYPES, classOf[Motive])
	implicit val potionRegistrable     : Registrable[Potion]       = Registrable.create(ForgeRegistries.Keys.POTIONS, classOf[Potion])
}
