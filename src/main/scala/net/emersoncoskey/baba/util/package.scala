package net.emersoncoskey.baba


import net.minecraft.world.entity.decoration.Motive
import net.minecraft.world.item.Item
import net.minecraft.world.item.alchemy.Potion
import net.minecraft.world.item.enchantment.Enchantment
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.material.Fluid

package object util {
	/** The list of default registry types */
	//type DefaultRegistries = Item #: Block #: Fluid #: Enchantment #: Potion #: PaintingType #: RNil

	/** A simple type alias. I don't know why Minecraft uses the name "Motive" */
	type PaintingType = Motive
}
