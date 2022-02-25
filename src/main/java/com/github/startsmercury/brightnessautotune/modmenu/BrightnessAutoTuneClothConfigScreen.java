package com.github.startsmercury.brightnessautotune.modmenu;

import com.github.startsmercury.brightnessautotune.clothconfig.ConfigBuilder;
import com.github.startsmercury.brightnessautotune.clothconfig.ConfigCategory;
import com.github.startsmercury.brightnessautotune.clothconfig.ConfigEntryBuilder;
import com.github.startsmercury.brightnessautotune.entrypoint.BrightnessAutoTuneClientMod;
import com.github.startsmercury.brightnessautotune.entrypoint.BrightnessAutoTuneClientMod.Config;

import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import me.shedaniel.clothconfig2.gui.entries.DoubleListEntry;
import me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder;
import me.shedaniel.clothconfig2.impl.builders.DoubleFieldBuilder;
import net.minecraft.client.gui.screens.Screen;

public class BrightnessAutoTuneClothConfigScreen {
	public static Screen create(final Screen parent) {
		final ConfigBuilder builder = new ConfigBuilder("brightness-auto-tune.config");
		final BrightnessAutoTuneClientMod brightnessAutoTuneClient = BrightnessAutoTuneClientMod.getInstance();
		final Config config = brightnessAutoTuneClient.getConfig();

		createMainCategory(config, builder);

		builder.setParentScreen(parent);
		builder.setSavingRunnable(() -> {
			brightnessAutoTuneClient.setConfig(config);
			brightnessAutoTuneClient.saveConfig();
		});
		builder.transparentBackground();

		return builder.build();
	}

	protected static DoubleListEntry createLeastBrightnessEntry(final Config config, final ConfigEntryBuilder builder) {
		final DoubleFieldBuilder itBuilder = builder.startDoubleField("least_brightness", config.getLeastBrightness());

		itBuilder.setDefaultValue(0.0D);
		itBuilder.setMax(1.0D);
		itBuilder.setMin(0.0D);
		itBuilder.setSaveConsumer(config::setLeastBrightness);

		return itBuilder.build();
	}

	protected static ConfigCategory createMainCategory(final Config config, final ConfigBuilder builder) {
		final ConfigCategory it = builder.getOrCreateCategory("main");

		it.addEntry(createTuneBrightnessEntry(config, it.entryBuilder()));
		it.addEntry(createTuneSpeedEntry(config, it.entryBuilder()));
		it.addEntry(createLeastBrightnessEntry(config, it.entryBuilder()));
		it.addEntry(createMostBrightnessEntry(config, it.entryBuilder()));

		return it;
	}

	protected static DoubleListEntry createMostBrightnessEntry(final Config config, final ConfigEntryBuilder builder) {
		final DoubleFieldBuilder itBuilder = builder.startDoubleField("most_brightness", config.getMostBrightness());

		itBuilder.setDefaultValue(1.0D);
		itBuilder.setMax(1.0D);
		itBuilder.setMin(0.0D);
		itBuilder.setSaveConsumer(config::setMostBrightness);

		return itBuilder.build();
	}

	protected static BooleanListEntry createTuneBrightnessEntry(final Config config, final ConfigEntryBuilder builder) {
		final BooleanToggleBuilder itBuilder = builder.startBooleanToggle("tune_brightness",
				config.shouldTuneBrightness());

		itBuilder.setDefaultValue(true);
		itBuilder.setSaveConsumer(config::setTuneBrightness);

		return itBuilder.build();
	}

	protected static DoubleListEntry createTuneSpeedEntry(final Config config, final ConfigEntryBuilder builder) {
		final DoubleFieldBuilder itBuilder = builder.startDoubleField("tune_speed", config.getTuneSpeed());

		itBuilder.setDefaultValue(0.01D);
		itBuilder.setMax(1.0D);
		itBuilder.setMin(0.0D);
		itBuilder.setSaveConsumer(config::setTuneSpeed);

		return itBuilder.build();
	}
}
