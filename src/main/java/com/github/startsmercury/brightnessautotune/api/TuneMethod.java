package com.github.startsmercury.brightnessautotune.api;

import java.util.function.DoubleSupplier;

public interface TuneMethod {
	TuneMethod LEGACY = (currentBrightness, targetBrightness,
			multiplier) -> (currentBrightness + multiplier.getAsDouble() * (targetBrightness - currentBrightness));

	double tune(double currentBrightness, double targetBrightness, DoubleSupplier multiplier);
}
