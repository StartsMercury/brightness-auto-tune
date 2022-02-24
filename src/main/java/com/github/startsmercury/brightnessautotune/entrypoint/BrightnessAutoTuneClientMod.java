package com.github.startsmercury.brightnessautotune.entrypoint;

import static net.fabricmc.api.EnvType.CLIENT;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Options;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;

@Environment(CLIENT)
public class BrightnessAutoTuneClientMod implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			final Options options = client.options;
			final ClientLevel level = client.level;
			final LocalPlayer player = client.player;

			if (level != null && player != null) {
				final double newGamma = (15 - level.getRawBrightness(player.blockPosition().above(), 0)) / 15.0D;

				options.gamma += (newGamma - options.gamma) / 10.0D;
			} else {
				options.gamma = 0.5D;
			}
		});
	}
}
