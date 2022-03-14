package cardboard.registry

import cardboard.data.RegList
import cardboard.registry.dsl.DecMod
import net.minecraftforge.registries.IForgeRegistryEntry

import java.util.function.Supplier

trait RegDec[A <: IForgeRegistryEntry[A]] {
	val name: String
	def sup: Supplier[A]
	val mods: Seq[DecMod[A]] = Nil
}
