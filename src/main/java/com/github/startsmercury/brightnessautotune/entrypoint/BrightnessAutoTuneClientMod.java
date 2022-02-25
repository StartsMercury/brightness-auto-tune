package com.github.startsmercury.brightnessautotune.entrypoint;

import static com.github.startsmercury.brightnessautotune.util.Clamp.clamp;
import static com.github.startsmercury.brightnessautotune.util.Nonnull.nonnull;
import static java.lang.Runtime.getRuntime;
import static net.fabricmc.api.EnvType.CLIENT;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.player.LocalPlayer;

@Environment(CLIENT)
public class BrightnessAutoTuneClientMod implements ClientModInitializer {
	public static class Config {
		private double leastBrightness;

		private double mostBrightness;

		private boolean tuneBrightness;

		private double tuneSpeed;

		public Config() {
			this(true);
		}

		public Config(final boolean tuneBrightness) {
			this(tuneBrightness, 0.01D);
		}

		public Config(final boolean tuneBrightness, final double tuneSpeed) {
			this(tuneBrightness, tuneSpeed, 0.0D, 1.0D);
		}

		public Config(final boolean tuneBrightness, final double tuneSpeed, final double leastBrightness,
				final double mostBrightness) {
			this.tuneBrightness = tuneBrightness;
			this.tuneSpeed = tuneSpeed;
			this.leastBrightness = leastBrightness;
			this.mostBrightness = mostBrightness;
		}

		public double getLeastBrightness() {
			return this.leastBrightness;
		}

		public double getMostBrightness() {
			return this.mostBrightness;
		}

		public double getTuneSpeed() {
			return this.tuneSpeed;
		}

		public Config setLeastBrightness(final double leastBrightness) {
			this.leastBrightness = clamp(0.0D, leastBrightness, 1.0D);

			return this;
		}

		public Config setMostBrightness(final double mostBrightness) {
			this.mostBrightness = clamp(0.0D, mostBrightness, 1.0D);

			return this;
		}

		public Config setTuneBrightness(final boolean tuneBrightness) {
			this.tuneBrightness = tuneBrightness;

			return this;
		}

		public Config setTuneSpeed(final double tuneSpeed) {
			this.tuneSpeed = clamp(0.0D, tuneSpeed, 1.0D);

			return this;
		}

		public boolean shouldTuneBrightness() {
			return this.tuneBrightness;
		}
	}

	private static @Nullable BrightnessAutoTuneClientMod instance;

	static {
		instance = null;
	}

	public static @Nonnull BrightnessAutoTuneClientMod getInstance() {
		if (instance == null) {
			throw new IllegalStateException("Accessed Brightness Auto Tune (Client) too early!");
		} else {
			return instance;
		}
	}

	private @Nonnull Config config;

	private final @Nonnull Path configPath;

	private @Nonnull Gson gson;

	private final @Nonnull Logger logger;

	public BrightnessAutoTuneClientMod() {
		this.configPath = FabricLoader.getInstance().getConfigDir().resolve(getId() + ".json");
		this.logger = LoggerFactory.getLogger(getId());

		setGson(new GsonBuilder().setPrettyPrinting().create());
		loadConfig();

		getRuntime().addShutdownHook(new Thread(this::saveConfig));
	}

	public @Nonnull Config getConfig() {
		return this.config;
	}

	public @Nonnull Path getConfigPath() {
		return this.configPath;
	}

	public @Nonnull Gson getGson() {
		return this.gson;
	}

	public @Nonnull String getId() {
		return "brightness-auto-tune";
	}

	public @Nonnull Logger getLogger() {
		return this.logger;
	}

	public void loadConfig() {
		try {
			setConfig(getGson().fromJson(Files.newBufferedReader(getConfigPath()), Config.class));
		} catch (final IOException | NullPointerException e) {
			getLogger().error("Config load exception.", e);

			setConfig(new Config());
			saveConfig();
		}
	}

	@Override
	public void onInitializeClient() {
		instance = this;

		ClientTickEvents.END_WORLD_TICK.register(level -> {
			final Config config = getConfig();

			if (!config.shouldTuneBrightness()) {
				return;
			}

			final Minecraft client = Minecraft.getInstance();
			final Options options = client.options;
			final LocalPlayer player = client.player;

			if (player != null) {
				final double leastBrightness = config.getLeastBrightness();
				final double mostBrightness = config.getMostBrightness();
				final double newGamma = level.getRawBrightness(player.blockPosition().above(), 0) *
						(leastBrightness - mostBrightness) / 15.0D + mostBrightness;

				options.gamma += config.getTuneSpeed() * (newGamma - options.gamma);
			} else {
				options.gamma = 0.5D;
			}
		});
	}

	public void saveConfig() {
		try {
			getGson().toJson(getConfig(), Files.newBufferedWriter(getConfigPath()));
		} catch (final IOException ioe) {
			getLogger().error("Config save exception.", ioe);
		}
	}

	public void setConfig(final @Nonnull Config config) {
		this.config = nonnull(config);
	}

	public void setGson(final @Nonnull Gson gson) {
		this.gson = nonnull(gson);
	}
}
