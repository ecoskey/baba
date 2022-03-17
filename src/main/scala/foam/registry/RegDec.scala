package foam.registry

import foam.data.RegList
import foam.registry.dsl.DecMod
import net.minecraftforge.registries.IForgeRegistryEntry

import java.util.function.Supplier

/** a Registry Declaration */
trait RegDec[A <: IForgeRegistryEntry[A]] {
	val name: String
	def supplier: Supplier[A]
	val modifiers: Seq[DecMod[A]] = Nil
}
