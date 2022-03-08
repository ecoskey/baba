package cardboard.registry

import cardboard.Cardboard.DefaultRegistries
import cardboard.CbMod
import cardboard.data.hlistops.in
import net.minecraftforge.registries.IForgeRegistryEntry

trait Reg {
	type A <: IForgeRegistryEntry[A]
	def reg: RegistryDec[A]
	//def get(implicit mod: CbMod, in: A in DefaultRegistries): A = mod.get(this)
}

object Reg {
	type Aux[X <: IForgeRegistryEntry[X]] = Reg { type A = X }
}
