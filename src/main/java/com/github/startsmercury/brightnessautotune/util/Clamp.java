package com.github.startsmercury.brightnessautotune.util;

public final class Clamp {
	public static double clamp(final double minValue, final double value, final double maxValue) {
		return value < minValue ? minValue : value > maxValue ? maxValue : value;
	}

	public static int clamp(final int minValue, final int value, final int maxValue) {
		return value < minValue ? minValue : value > maxValue ? maxValue : value;
	}

	private Clamp() {
	}
}
