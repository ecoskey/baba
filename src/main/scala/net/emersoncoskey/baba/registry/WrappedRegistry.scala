package net.emersoncoskey.baba.registry

import net.emersoncoskey.baba.BaseMod
import net.minecraftforge.registries.{DeferredRegister, IForgeRegistry, IForgeRegistryEntry, RegistryObject}

import scala.collection.mutable

class WrappedRegistry[A <: IForgeRegistryEntry[A]](mod: BaseMod, registry: IForgeRegistry[A]) {
	private val _registry: DeferredRegister[A] = DeferredRegister.create[A](registry, mod.ModId)
	private val map: mutable.Map[RegDec[A], RegistryObject[A]] = mutable.Map[RegDec[A], RegistryObject[A]]()

	private[baba] def register(dec: RegDec[A]): Unit = map.addOne(dec -> _registry.register(dec.name, dec.supplier))

	def get(dec: RegDec[A]): RegistryObject[A] =
		map.getOrElse(dec, throw new IllegalAccessException(s"registry declaration ${dec.name} has not been registered yet."))

	def apply(dec: RegDec[A]): RegistryObject[A] = get(dec)

	def getAll: List[A] = map.values.map(_.get).toList

	_registry.register(mod.EventBus)
}
