package com.github.startsmercury.brightnessautotune.modmenu;

import static net.fabricmc.api.EnvType.CLIENT;

import com.github.startsmercury.brightnessautotune.clothconfig.ConfigBuilder;
import com.github.startsmercury.brightnessautotune.clothconfig.ConfigCategory;
import com.github.startsmercury.brightnessautotune.clothconfig.ConfigEntryBuilder;
import com.github.startsmercury.brightnessautotune.entrypoint.BrightnessAutoTuneClientMod;
import com.github.startsmercury.brightnessautotune.entrypoint.BrightnessAutoTuneClientMod.Config;

import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import me.shedaniel.clothconfig2.gui.entries.DoubleListEntry;
import me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder;
import me.shedaniel.clothconfig2.impl.builders.DoubleFieldBuilder;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

@Environment(CLIENT)
public class BrightnessAutoTuneClothConfigScreen {
	public static Screen create(final Screen parent) {
		final ConfigBuilder builder = new ConfigBuilder("brightness-auto-tune.config");
		final BrightnessAutoTuneClientMod brightnessAutoTuneClient = BrightnessAutoTuneClientMod.getInstance();
		final Config config = new Config(brightnessAutoTuneClient.getConfig());

		createMainCategory(config, builder);

		builder.setParentScreen(parent);
		builder.setSavingRunnable(() -> {
			brightnessAutoTuneClient.setConfig(config);

			brightnessAutoTuneClient.saveConfig();
		});
		builder.transparentBackground();

		return builder.build();
	}

	protected static DoubleListEntry createCurrentBrightnessEntry(final Config config,
			final ConfigEntryBuilder builder) {
		@SuppressWarnings("resource")
		final DoubleFieldBuilder itBuilder = builder.startDoubleField("current_brightness",
				Minecraft.getInstance().options.gamma);

		itBuilder.setDefaultValue(config::getDefaultBrightness);
		itBuilder.setMax(1.0D);
		itBuilder.setMin(0.0D);
		itBuilder.setSaveConsumer(gamma -> Minecraft.getInstance().options.gamma = gamma);

		return itBuilder.build();
	}

	protected static DoubleListEntry createDefaultBrightnessEntry(final Config config,
			final ConfigEntryBuilder builder) {
		final DoubleFieldBuilder itBuilder = builder.startDoubleField("default_brightness",
				config.getDefaultBrightness());

		itBuilder.setDefaultValue(0.5D);
		itBuilder.setMax(1.0D);
		itBuilder.setMin(0.0D);
		itBuilder.setSaveConsumer(config::setDefaultBrightness);

		return itBuilder.build();
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

		it.addEntry(createCurrentBrightnessEntry(config, it.entryBuilder()));
		it.addEntry(createTuneBrightnessEntry(config, it.entryBuilder()));
		it.addEntry(createTuneSpeedEntry(config, it.entryBuilder()));
		it.addEntry(createDefaultBrightnessEntry(config, it.entryBuilder()));
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
		itBuilder.setSaveConsumer(value -> {
			final Minecraft minecraft = Minecraft.getInstance();

			if (value) {
				config.setBrightness(minecraft.options.gamma);

				minecraft.options.gamma = config.getDefaultBrightness();
			} else {
				minecraft.options.gamma = config.getBrightness();
			}

			config.setTuneBrightness(value);
		});

		return itBuilder.build();
	}

	protected static DoubleListEntry createTuneSpeedEntry(final Config config, final ConfigEntryBuilder builder) {
		final DoubleFieldBuilder itBuilder = builder.startDoubleField("tune_speed", config.getTuneMultiplier());

		itBuilder.setDefaultValue(0.08D);
		itBuilder.setMax(1.0D);
		itBuilder.setMin(0.0D);
		itBuilder.setSaveConsumer(config::setTuneMultiplier);

		return itBuilder.build();
	}
}
