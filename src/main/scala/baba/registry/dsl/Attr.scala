package baba.registry.dsl

trait Attr[R, -I] {
	def :=(value: I): DecMod[R]
}
