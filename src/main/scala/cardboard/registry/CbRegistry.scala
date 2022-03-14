package cardboard.registry

import cardboard.CbMod
import net.minecraftforge.registries.{DeferredRegister, IForgeRegistry, IForgeRegistryEntry, RegistryObject}

import scala.collection.mutable

class CbRegistry[A <: IForgeRegistryEntry[A]](mod: CbMod, registry: IForgeRegistry[A]) {
	private val _registry: DeferredRegister[A] = DeferredRegister.create(registry, mod.ModId)
	private val map: mutable.Map[RegDec.Aux[A], RegistryObject[A]] = mutable.Map[RegDec.Aux[A], RegistryObject[A]]()

	private [cardboard] def register(dec: RegDec.Aux[A]): Unit =
		map.addOne(dec -> _registry.register(dec.name, dec.sup))

	def get(dec: RegDec.Aux[A]): RegistryObject[A] =
		map.getOrElse(dec, throw new IllegalAccessException(s"registry declaration ${dec.name} has not been registered yet."))

	def apply(dec: RegDec.Aux[A]): RegistryObject[A] = get(dec)

	_registry.register(mod.EventBus)
}
