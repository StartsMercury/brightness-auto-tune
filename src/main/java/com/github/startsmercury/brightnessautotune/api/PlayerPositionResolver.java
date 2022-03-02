package com.github.startsmercury.brightnessautotune.api;

import static net.fabricmc.api.EnvType.CLIENT;

import net.fabricmc.api.Environment;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

@Environment(CLIENT)
public interface PlayerPositionResolver {
	PlayerPositionResolver LEGACY = Player::getEyePosition;

	Vec3 resolvePosition(Player player);
}
