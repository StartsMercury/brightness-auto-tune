package com.github.startsmercury.brightnessautotune.util;

import net.minecraft.network.chat.TranslatableComponent;

public final class FormattedTextMessages {
	public static TranslatableComponent translatable(final String string, final Object... objects) {
		return new TranslatableComponent(string, objects);
	}

	private FormattedTextMessages() {
	}
}
