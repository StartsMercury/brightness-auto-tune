package com.github.startsmercury.brightnessautotune.util;

import static com.github.startsmercury.brightnessautotune.util.Nonnull.nonnull;

import javax.annotation.Nonnull;

public final class Nullable {
	public static <T> @javax.annotation.Nullable T nullable(@javax.annotation.Nullable final T value,
			@Nonnull final T defaultValue) {
		if (value == null) {
			return nonnull(defaultValue);
		} else {
			return value;
		}
	}

	private Nullable() {
	}
}
