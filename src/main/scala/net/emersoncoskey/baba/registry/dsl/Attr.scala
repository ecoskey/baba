package net.emersoncoskey.baba.registry.dsl

import net.emersoncoskey.baba.registry.EventHandler

trait Attr[-R, -I] {
	def :=(value: I): R => EventHandler
}
