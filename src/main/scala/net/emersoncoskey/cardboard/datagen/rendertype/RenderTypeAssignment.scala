package net.emersoncoskey.cardboard.datagen.rendertype

import net.minecraft.client.renderer.RenderType

case class RenderTypeAssignment[A](obj: A, renderType: RenderType)
