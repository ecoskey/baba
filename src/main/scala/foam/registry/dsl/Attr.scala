package foam.registry.dsl

trait Attr[R, -I] {
	def :=(value: I): DecMod[R]
}
