package net.emersoncoskey.baba

import net.emersoncoskey.baba.registry.declarations.AllDecs
import net.emersoncoskey.baba.registry.mods.AllMods

package object registry
  extends RegisterApi
  with AllDecs
  with AllMods
