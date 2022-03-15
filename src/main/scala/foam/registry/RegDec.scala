package foam.registry

import foam.data.RegList
import foam.registry.dsl.DecMod
import net.minecraftforge.registries.IForgeRegistryEntry

import java.util.function.Supplier

trait RegDec[A <: IForgeRegistryEntry[A]] {
	val name: String
	def sup: Supplier[A]
	val mods: Seq[DecMod[A]] = Nil
}
