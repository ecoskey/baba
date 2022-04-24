package net.emersoncoskey.baba.registry.declarations

import net.emersoncoskey.baba.registry.{Register, declare}
import net.minecraft.world.entity.player.Inventory
import net.minecraft.world.inventory.{AbstractContainerMenu, MenuType}
import net.minecraftforge.registries.RegistryObject

trait MenuTypeDecs {
	def menuType[M <: AbstractContainerMenu](name: String, menuCtor: (Int, Inventory) => M): Register[RegistryObject[MenuType[M]]] =
		declare[MenuType[_], MenuType[M]](name, new MenuType((int, inventory) => menuCtor(int, inventory)))
}
