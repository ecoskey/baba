package net.emersoncoskey.baba.registry.declarations

import net.emersoncoskey.baba.registry._
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.material.Material
import net.minecraftforge.registries.RegistryObject

trait BlockDecs {
	def block(name: String, mods: SimpleDecMod[Block]*): McAction[RegistryObject[Block]] =
		block(name, new Block(_), mods:_*)

	def block(name: String, props: => Properties, mods: SimpleDecMod[Block]*): McAction[RegistryObject[Block]] =
		block(name, new Block(_), props, mods:_*)

	def block[B <: Block](name: String, ctor: Properties => B, mods: DecMod[Block, B]*): McAction[RegistryObject[B]] =
		block(name, ctor, Properties.of(Material.STONE), mods:_*)

	def block[B <: Block](name: String, ctor: Properties => B, props: => Properties, mods: DecMod[Block, B]*): McAction[RegistryObject[B]] =
		declareWithMods(name, ctor(props), mods:_*)
}
