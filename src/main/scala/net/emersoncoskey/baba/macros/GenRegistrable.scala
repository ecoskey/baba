package net.emersoncoskey.baba.macros

import net.emersoncoskey.baba.registry.Registrable
import net.minecraft.core.Registry
import net.minecraft.resources.ResourceKey
import net.minecraftforge.registries.{ForgeRegistries, IForgeRegistryEntry}

import scala.reflect.macros.blackbox

private[baba] object GenRegistrable {
	def impl[R <: IForgeRegistryEntry[R]](c: blackbox.Context)(implicit tag: c.WeakTypeTag[R]): c.Expr[Registrable[R]] = {
		import c.universe._
		val regKeys = c.typeOf[ForgeRegistries.Keys]

		println("amongus")

		val member = regKeys.decls.find(_.typeSignature <:< c.weakTypeOf[ResourceKey[Registry[R]]])
		                    .getOrElse(c.abort(c.enclosingPosition, s"Cannot find RegistryKey for ${tag.tpe} in ForgeRegistries.Keys"))

		c.Expr[Registrable[R]](q"new Registrable[${tag.tpe}]($member, classOf[${tag.tpe}])")
	}
}
