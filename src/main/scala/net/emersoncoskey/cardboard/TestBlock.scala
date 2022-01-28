package net.emersoncoskey.cardboard


import net.minecraft.core.BlockPos
import net.minecraft.world.effect.{MobEffectInstance, MobEffects}
import net.minecraft.world.entity.{Entity, LivingEntity}
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.block.state.BlockState

class TestBlock(props: Properties) extends Block(props) {
	override def stepOn(pLevel: Level, pPos: BlockPos, pState: BlockState, pEntity: Entity): Unit =
		if (!pLevel.isClientSide) {
			pEntity match {
				case entity: LivingEntity => entity.addEffect(new MobEffectInstance(MobEffects.JUMP, 200, 200))
			}
		}
}
