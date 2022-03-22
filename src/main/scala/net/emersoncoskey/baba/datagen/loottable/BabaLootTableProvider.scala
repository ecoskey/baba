package net.emersoncoskey.baba.datagen.loottable

import com.mojang.datafixers.util.Pair
import net.minecraft.data.DataGenerator
import net.minecraft.data.loot.LootTableProvider
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.storage.loot.LootTable
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet

import java.util
import java.util.function.{BiConsumer, Consumer, Supplier}

class BabaLootTableProvider(gen: DataGenerator) extends LootTableProvider(gen) {
	override def getTables: util.List[Pair[Supplier[Consumer[BiConsumer[ResourceLocation, LootTable.Builder]]], LootContextParamSet]] = ???
}
