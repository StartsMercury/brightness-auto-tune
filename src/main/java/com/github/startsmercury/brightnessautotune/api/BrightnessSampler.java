package com.github.startsmercury.brightnessautotune.api;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static net.fabricmc.api.EnvType.CLIENT;
import static net.minecraft.world.effect.MobEffects.BLINDNESS;
import static net.minecraft.world.effect.MobEffects.NIGHT_VISION;
import static net.minecraft.world.level.LightLayer.BLOCK;
import static net.minecraft.world.level.LightLayer.SKY;

import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;

@Environment(CLIENT)
public interface BrightnessSampler {
	BrightnessSampler LEGACY = (level, player, position) -> {
		if (player.hasEffect(BLINDNESS) /* || player.hasEffect(MobEffects.DARKNESS) */) {
			return 1.0D;
		} else if (player.isOnFire() || player.hasEffect(NIGHT_VISION)) {
			return 0.0D;
		} else {
			final BlockPos blockPos = new BlockPos(position);

			final DimensionType dimensionType = level.dimensionType();
			final int blockLight = level.getBrightness(BLOCK, blockPos);
			final int skyLight = level.getBrightness(SKY, blockPos) - level.getSkyDarken();
			final double sun = 2.0D * abs(0.5D - level.getTimeOfDay(0.0F));

			return max(sun * dimensionType.brightness(skyLight), dimensionType.brightness(blockLight));
		}
	};

	double sampleBrightness(final Level level, final Player player, final Vec3 position);
}
