package cardboard.registry.dsl

trait Attr[R, -I] {
	def :=(value: I): DecMod[R]
}
