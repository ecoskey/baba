package cardboard.registry

import cardboard.CbMod
import net.minecraftforge.registries.{DeferredRegister, IForgeRegistry, IForgeRegistryEntry, RegistryObject}

import scala.collection.mutable

class CbRegistry[A <: IForgeRegistryEntry[A]](mod: CbMod, registry: IForgeRegistry[A]) {
	private val register: DeferredRegister[A] = DeferredRegister.create(registry, mod.ModId)
	private val map: mutable.Map[RegistryDec[A], RegistryObject[A]] = mutable.Map[RegistryDec[A], RegistryObject[A]]()

	private [cardboard] def register(dec: RegistryDec[A]): Unit =
		map.addOne(dec -> register.register(dec.name, dec.sup))

	def get(dec: RegistryDec[A]): RegistryObject[A] =
		map.getOrElse(dec, throw new IllegalAccessException(s"registry declaration ${dec.name} has not been registered yet."))

	def apply(dec: RegistryDec[A]): RegistryObject[A] = get(dec)
}

object CbRegistry {
	def apply[A <: IForgeRegistryEntry[A]](implicit registry: CbRegistry[A]): CbRegistry[A] = registry
}
