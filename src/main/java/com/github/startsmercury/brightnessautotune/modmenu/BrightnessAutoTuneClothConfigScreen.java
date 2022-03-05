package com.github.startsmercury.brightnessautotune.modmenu;

import static net.fabricmc.api.EnvType.CLIENT;

import com.github.startsmercury.brightnessautotune.client.BrightnessAutoTuneConfig;
import com.github.startsmercury.brightnessautotune.clothconfig.ConfigBuilder;
import com.github.startsmercury.brightnessautotune.clothconfig.ConfigCategory;
import com.github.startsmercury.brightnessautotune.clothconfig.ConfigEntryBuilder;
import com.github.startsmercury.brightnessautotune.entrypoint.BrightnessAutoTuneClientMod;

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
		final BrightnessAutoTuneConfig brightnessAutoTuneConfig = new BrightnessAutoTuneConfig(
				brightnessAutoTuneClient.getConfig());

		createMainCategory(brightnessAutoTuneConfig, builder);

		builder.setParentScreen(parent);
		builder.setSavingRunnable(() -> {
			brightnessAutoTuneClient.setConfig(brightnessAutoTuneConfig);

			brightnessAutoTuneClient.saveConfig();
		});
		builder.transparentBackground();

		return builder.build();
	}

	protected static DoubleListEntry createCurrentBrightnessEntry(
			final BrightnessAutoTuneConfig brightnessAutoTuneConfig, final ConfigEntryBuilder builder) {
		@SuppressWarnings("resource")
		final DoubleFieldBuilder itBuilder = builder.startDoubleField("current_brightness",
				Minecraft.getInstance().options.gamma);

		itBuilder.setDefaultValue(brightnessAutoTuneConfig::getDefaultBrightness);
		itBuilder.setMax(1.0D);
		itBuilder.setMin(0.0D);
		itBuilder.setSaveConsumer(gamma -> Minecraft.getInstance().options.gamma = gamma);

		return itBuilder.build();
	}

	protected static DoubleListEntry createDefaultBrightnessEntry(
			final BrightnessAutoTuneConfig brightnessAutoTuneConfig, final ConfigEntryBuilder builder) {
		final DoubleFieldBuilder itBuilder = builder.startDoubleField("default_brightness",
				brightnessAutoTuneConfig.getDefaultBrightness());

		itBuilder.setDefaultValue(0.5D);
		itBuilder.setMax(1.0D);
		itBuilder.setMin(0.0D);
		itBuilder.setSaveConsumer(brightnessAutoTuneConfig::setDefaultBrightness);

		return itBuilder.build();
	}

	protected static DoubleListEntry createLeastBrightnessEntry(final BrightnessAutoTuneConfig brightnessAutoTuneConfig,
			final ConfigEntryBuilder builder) {
		final DoubleFieldBuilder itBuilder = builder.startDoubleField("least_brightness",
				brightnessAutoTuneConfig.getLeastBrightness());

		itBuilder.setDefaultValue(0.0D);
		itBuilder.setMax(1.0D);
		itBuilder.setMin(0.0D);
		itBuilder.setSaveConsumer(brightnessAutoTuneConfig::setLeastBrightness);

		return itBuilder.build();
	}

	protected static ConfigCategory createMainCategory(final BrightnessAutoTuneConfig brightnessAutoTuneConfig,
			final ConfigBuilder builder) {
		final ConfigCategory it = builder.getOrCreateCategory("main");

		it.addEntry(createCurrentBrightnessEntry(brightnessAutoTuneConfig, it.entryBuilder()));
		it.addEntry(createTuneBrightnessEntry(brightnessAutoTuneConfig, it.entryBuilder()));
		it.addEntry(createTuneSpeedEntry(brightnessAutoTuneConfig, it.entryBuilder()));
		it.addEntry(createDefaultBrightnessEntry(brightnessAutoTuneConfig, it.entryBuilder()));
		it.addEntry(createLeastBrightnessEntry(brightnessAutoTuneConfig, it.entryBuilder()));
		it.addEntry(createMostBrightnessEntry(brightnessAutoTuneConfig, it.entryBuilder()));

		return it;
	}

	protected static DoubleListEntry createMostBrightnessEntry(final BrightnessAutoTuneConfig brightnessAutoTuneConfig,
			final ConfigEntryBuilder builder) {
		final DoubleFieldBuilder itBuilder = builder.startDoubleField("most_brightness",
				brightnessAutoTuneConfig.getMostBrightness());

		itBuilder.setDefaultValue(1.0D);
		itBuilder.setMax(1.0D);
		itBuilder.setMin(0.0D);
		itBuilder.setSaveConsumer(brightnessAutoTuneConfig::setMostBrightness);

		return itBuilder.build();
	}

	protected static BooleanListEntry createTuneBrightnessEntry(final BrightnessAutoTuneConfig brightnessAutoTuneConfig,
			final ConfigEntryBuilder builder) {
		final BooleanToggleBuilder itBuilder = builder.startBooleanToggle("tune_brightness",
				brightnessAutoTuneConfig.shouldTuneBrightness());

		itBuilder.setDefaultValue(true);
		itBuilder.setSaveConsumer(value -> {
			final Minecraft minecraft = Minecraft.getInstance();

			if (value) {
				brightnessAutoTuneConfig.setBrightness(minecraft.options.gamma);

				minecraft.options.gamma = brightnessAutoTuneConfig.getDefaultBrightness();
			} else {
				minecraft.options.gamma = brightnessAutoTuneConfig.getBrightness();
			}

			brightnessAutoTuneConfig.setTuneBrightness(value);
		});

		return itBuilder.build();
	}

	protected static DoubleListEntry createTuneSpeedEntry(final BrightnessAutoTuneConfig brightnessAutoTuneConfig,
			final ConfigEntryBuilder builder) {
		final DoubleFieldBuilder itBuilder = builder.startDoubleField("tune_speed",
				brightnessAutoTuneConfig.getTuneMultiplier());

		itBuilder.setDefaultValue(0.08D);
		itBuilder.setMax(1.0D);
		itBuilder.setMin(0.0D);
		itBuilder.setSaveConsumer(brightnessAutoTuneConfig::setTuneMultiplier);

		return itBuilder.build();
	}
}
