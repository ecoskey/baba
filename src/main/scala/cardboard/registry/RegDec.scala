package cardboard.registry

import cardboard.data.HList
import cardboard.data.hlistops.get
import cardboard.registry.dsl.DecMod
import net.minecraftforge.registries.IForgeRegistryEntry

import java.util.function.Supplier

trait RegDec {
	type A <: IForgeRegistryEntry[A]
	val name: String
	def sup: Supplier[A]
	val mods: Seq[DecMod[A]] = Nil

	def getRegistryFor[L <: HList](l: L)(implicit get: L get A): Option[CbRegistry[A]] = get(l)
}

object RegDec {
	type Aux[X <: IForgeRegistryEntry[X]] = RegDec { type A = X }
}
