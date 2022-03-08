package cardboard

import cardboard.registry.Reg


trait CbModule {
	val registryDecs: Seq[Reg[_]]
}