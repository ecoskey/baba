package net.emersoncoskey.cardboard

import net.emersoncoskey.cardboard.data./~\
import net.emersoncoskey.cardboard.registry.RegistryDec
import net.minecraftforge.registries.{IForgeRegistry, IForgeRegistryEntry}

trait CbModule {
	type RegSeq[A <: IForgeRegistryEntry[A]] = Seq[RegistryDec[A]]
	val reg: Seq[IForgeRegistry /~\ RegSeq]
}