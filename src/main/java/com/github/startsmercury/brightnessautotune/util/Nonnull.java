package com.github.startsmercury.brightnessautotune.util;

public final class Nonnull {
	public static <T> @javax.annotation.Nonnull T nonnull(final @javax.annotation.Nonnull T value)
			throws NullPointerException {
		if (value == null) {
			throw new NullPointerException();
		} else {
			return value;
		}
	}

	private Nonnull() {
	}
}
