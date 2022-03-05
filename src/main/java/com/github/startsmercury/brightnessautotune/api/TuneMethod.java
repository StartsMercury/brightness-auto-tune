package com.github.startsmercury.brightnessautotune.api;

import static net.fabricmc.api.EnvType.CLIENT;

import java.util.function.DoubleSupplier;

import net.fabricmc.api.Environment;

@Environment(CLIENT)
public interface TuneMethod {
	TuneMethod LEGACY = (currentBrightness, targetBrightness, multiplier) -> currentBrightness +
			multiplier.getAsDouble() * (targetBrightness - currentBrightness);

	double tune(double currentBrightness, double targetBrightness, DoubleSupplier multiplier);
}
