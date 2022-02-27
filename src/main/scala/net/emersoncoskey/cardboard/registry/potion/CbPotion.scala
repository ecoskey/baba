package net.emersoncoskey.cardboard.registry.potion

import net.emersoncoskey.cardboard.registry.{Reg, RegistryDec}
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.item.alchemy.Potion
import net.minecraftforge.registries.{ForgeRegistries, IForgeRegistry}

class CbPotion[+P <: Potion] private (
	val name: String,
	val ctor: Seq[MobEffectInstance] => P,
	val effects: Seq[MobEffectInstance]
)

object CbPotion {
	implicit val regInstance: Reg[CbPotion, Potion] = new Reg[CbPotion, Potion] {
		override val registry: IForgeRegistry[Potion] = ForgeRegistries.POTIONS

		override def reg(r: CbPotion[Potion]): RegistryDec[Potion] = RegistryDec(r.name, () => r.ctor(r.effects))
	}

	def apply(name: String, effects: MobEffectInstance*): CbPotion[Potion] = new CbPotion[Potion](name, new Potion(_:_*), effects)
	def apply[P <: Potion](name: String, ctor: Seq[MobEffectInstance] => P, effects: MobEffectInstance*) = new CbPotion[P](name, ctor, effects)
}
