package cardboard

import cardboard.registry.RegDec


abstract class CbModule {
	//type Regs = ev.Out
	def decs: Seq[RegDec]
}