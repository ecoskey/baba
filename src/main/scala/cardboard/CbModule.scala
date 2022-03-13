package cardboard

import cardboard.CbModule.RegSeq
import cardboard.registry.RegDec
import cardboard.shapelesscompat.hlist.ops.FBoundMapped
import net.minecraftforge.registries.IForgeRegistryEntry
import shapeless.HList


abstract class CbModule[R <: HList](implicit ev: FBoundMapped[R, RegSeq, IForgeRegistryEntry]) {
	//type Regs = ev.Out
	def decs: R
}

object CbModule {
	type RegSeq[A <: IForgeRegistryEntry[A]] = Seq[RegDec[A]]
}