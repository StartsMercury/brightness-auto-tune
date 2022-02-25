package com.github.startsmercury.brightnessautotune.util;

import javax.annotation.Nullable;

public final class Nonnull {
	public static <T> @javax.annotation.Nonnull T nonnull(final @Nullable T value) throws NullPointerException {
		if (value == null) {
			throw new NullPointerException();
		} else {
			return value;
		}
	}

	private Nonnull() {
	}
}
