package net.emersoncoskey.baba.registry

import net.minecraftforge.registries.{IForgeRegistryEntry, RegistryObject}

sealed trait McActionA[+A]

case class Declare[R <: IForgeRegistryEntry[R], A <: R] private(name: String, getObj: () => A)(implicit val ev: Registrable[R])
  extends McActionA[RegistryObject[A]]

case class HandleEvent private(handler: EventHandler) extends McActionA[Unit]