package baba.registry

import baba.data.RegList
import baba.registry.dsl.DecMod
import net.minecraftforge.registries.IForgeRegistryEntry

import java.util.function.Supplier

/** a Registry Declaration */
trait RegDec[A <: IForgeRegistryEntry[A]] {
	val name: String
	def supplier: Supplier[A]
	val modifiers: Seq[DecMod[A]] = Nil
}
