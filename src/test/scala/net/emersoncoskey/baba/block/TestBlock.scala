package net.emersoncoskey.baba.block

import net.minecraft.core.BlockPos
import net.minecraft.world.{InteractionHand, InteractionResult}
import net.minecraft.world.effect.{MobEffectInstance, MobEffects}
import net.minecraft.world.entity.player.Player
import net.minecraft.world.entity.{Entity, LivingEntity}
import net.minecraft.world.level.{Explosion, Level}
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour.Properties
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class TestBlock(props: Properties) extends Block(props) {
	override def stepOn(pLevel: Level, pPos: BlockPos, pState: BlockState, pEntity: Entity): Unit = if (!pLevel.isClientSide) {
		pEntity match {
			case entity: LivingEntity => entity.addEffect(new MobEffectInstance(MobEffects.JUMP, 200, 200))
			case _ => ()
		}
	}

	override def use(
		pState : BlockState,
		pLevel : Level,
		pPos   : BlockPos,
		pPlayer: Player,
		pHand  : InteractionHand,
		pHit   : BlockHitResult
	): InteractionResult = {
		if (!pLevel.isClientSide) {
			pLevel.explode(null, pPos.getX, pPos.getY, pPos.getZ, 10.0f, true, Explosion.BlockInteraction.BREAK)
		}
		InteractionResult.SUCCESS
	}
}
