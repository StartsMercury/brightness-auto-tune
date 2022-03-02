package com.github.startsmercury.brightnessautotune.entrypoint;

import static com.github.startsmercury.brightnessautotune.util.Clamp.clamp;
import static com.github.startsmercury.brightnessautotune.util.Nonnull.nonnull;
import static com.github.startsmercury.brightnessautotune.util.Nullable.nullable;
import static java.lang.Runtime.getRuntime;
import static net.fabricmc.api.EnvType.CLIENT;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.startsmercury.brightnessautotune.api.BrightnessSampler;
import com.github.startsmercury.brightnessautotune.api.PlayerPositionResolver;
import com.github.startsmercury.brightnessautotune.api.TuneMethod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

@Environment(CLIENT)
public class BrightnessAutoTuneClientMod implements ClientModInitializer {
	public static class Config implements Cloneable, Serializable {
		@Serial
		private static final long serialVersionUID = -1252132672103122771L;

		private double brightness;

		private double defaultBrightness;

		private double leastBrightness;

		private double mostBrightness;

		private boolean tuneBrightness;

		private double tuneMultiplier;

		public Config() {
			this(true);
		}

		public Config(final boolean tuneBrightness) {
			this(tuneBrightness, 0.08D);
		}

		public Config(final boolean tuneBrightness, final double tuneMultiplier) {
			this(tuneBrightness, tuneMultiplier, 0.5D);
		}

		public Config(final boolean tuneBrightness, final double tuneMultiplier, final double defaultBrightness) {
			this(tuneBrightness, tuneMultiplier, defaultBrightness, 0.0D, 0.5D);
		}

		public Config(final boolean tuneBrightness, final double tuneMultiplier, final double defaultBrightness,
				final double leastBrightness, final double mostBrightness) {
			this.brightness = 0.5D;
			this.defaultBrightness = clamp(0.0D, defaultBrightness, 1.0D);
			this.leastBrightness = clamp(0.0D, leastBrightness, 1.0D);
			this.mostBrightness = clamp(0.0D, mostBrightness, 1.0D);
			this.tuneBrightness = tuneBrightness;
			this.tuneMultiplier = clamp(0.0D, tuneMultiplier, 1.0D);
		}

		public Config(final Config other) {
			this(other.shouldTuneBrightness(), other.getTuneMultiplier(), other.getDefaultBrightness(),
					other.getLeastBrightness(), other.getMostBrightness());
		}

		@Override
		public Config clone() {
			try {
				return ((Config) super.clone()).setBrightness(0.5D);
			} catch (final CloneNotSupportedException cnse) {
				throw new InternalError(cnse);
			}
		}

		public Config copy(final Config other) {
			// @formatter:off
			return this.setDefaultBrightness(other.getDefaultBrightness())
			           .setLeastBrightness(other.getLeastBrightness())
			           .setMostBrightness(other.getMostBrightness())
			           .setTuneBrightness(other.shouldTuneBrightness())
			           .setTuneMultiplier(other.getTuneMultiplier());
			// @formatter:on
		}

		protected boolean equals(final Config other) {
			// @formatter:off
			return getDefaultBrightness() == other.getDefaultBrightness() &&
			       getLeastBrightness() == other.getLeastBrightness() &&
			       getMostBrightness() == other.getMostBrightness() &&
			       shouldTuneBrightness() == other.shouldTuneBrightness() &&
			       getTuneMultiplier() == other.getTuneMultiplier();
			// @formatter:on
		}

		@Override
		public boolean equals(final Object obj) {
			return this == obj ? true : obj instanceof final Config other ? equals(other) : false;
		}

		public double getBrightness() {
			return this.brightness;
		}

		public double getDefaultBrightness() {
			return clamp(0.0D, this.defaultBrightness, 1.0D);
		}

		public double getLeastBrightness() {
			return this.leastBrightness;
		}

		public double getMostBrightness() {
			return this.mostBrightness;
		}

		public double getTuneMultiplier() {
			return this.tuneMultiplier;
		}

		@Override
		public int hashCode() {
			// @formatter:off
			return ((((31 + Boolean.hashCode(shouldTuneBrightness()))
			         * 31 + Double.hashCode(getTuneMultiplier()))
			         * 31 + Double.hashCode(getDefaultBrightness()))
			         * 31 + Double.hashCode(getLeastBrightness()))
			         * 31 + Double.hashCode(getMostBrightness());
			// @formatter:on
		}

		public Config setBrightness(final double brightness) {
			this.brightness = clamp(0.0D, brightness, 1.0D);

			return this;
		}

		public Config setDefaultBrightness(final double defaultBrightness) {
			this.defaultBrightness = defaultBrightness;

			return this;
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

		public Config setTuneMultiplier(final double tuneMultiplier) {
			this.tuneMultiplier = clamp(0.0D, tuneMultiplier, 1.0D);

			return this;
		}

		public boolean shouldTuneBrightness() {
			return this.tuneBrightness;
		}

		@Override
		public String toString() {
			// @formatter:off
			return '{' + "\"tuneBrightness\":" + shouldTuneBrightness() + ','
			           + "\"tuneMultiplier\":" + getTuneMultiplier() + ','
			           + "\"defaultBrightnes\":" + getDefaultBrightness() + ','
			           + "\"leastBrightnes\":" + getLeastBrightness() + ','
			           + "\"mostBrightnes\":" + getMostBrightness() + '}';
			// @formatter:on
		}
	}

	private static @Nullable BrightnessAutoTuneClientMod instance = null;

	public static @Nonnull BrightnessAutoTuneClientMod getInstance() {
		if (instance == null) {
			throw new IllegalStateException("Accessed Brightness Auto Tune (Client) too early!");
		} else {
			return instance;
		}
	}

	private @Nonnull BrightnessSampler brightnessSampler;

	private @Nonnull Config config;

	private final @Nonnull Path configPath;

	private @Nonnull Gson gson;

	private final @Nonnull Logger logger;

	private @Nonnull PlayerPositionResolver positionResolver;

	private @Nonnull TuneMethod tuneMethod;

	public BrightnessAutoTuneClientMod() {
		this.brightnessSampler = BrightnessSampler.LEGACY;
		this.configPath = FabricLoader.getInstance().getConfigDir().resolve(getId() + ".json");
		this.gson = new GsonBuilder().setPrettyPrinting().create();
		this.logger = LoggerFactory.getLogger(getId());
		this.positionResolver = PlayerPositionResolver.LEGACY;
		this.tuneMethod = TuneMethod.LEGACY;

		loadConfig();

		getRuntime().addShutdownHook(new Thread(this::saveConfig));
	}

	public BrightnessSampler getBrightnessSampler() {
		return this.brightnessSampler;
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

	public PlayerPositionResolver getPositionResolver() {
		return this.positionResolver;
	}

	public TuneMethod getTuneMethod() {
		return this.tuneMethod;
	}

	public BrightnessAutoTuneClientMod loadConfig() {
		try (final BufferedReader in = Files.newBufferedReader(getConfigPath())) {
			setConfig(getGson().fromJson(in, Config.class));
		} catch (final IOException | NullPointerException e) {
			getLogger().error("Config load exception.", e);

			setConfig(new Config());
			saveConfig();
		}

		return this;
	}

	@Override
	public void onInitializeClient() {
		instance = this;

		registerListeners();
	}

	protected void registerListeners() {
		ClientTickEvents.END_WORLD_TICK.register(level -> {
			final Config config = getConfig();

			if (!config.shouldTuneBrightness()) {
				return;
			}

			final Minecraft client = Minecraft.getInstance();
			final LocalPlayer player = client.player;

			if (player != null) {
				final double leastBrightness = config.getLeastBrightness();
				final double mostBrightness = config.getMostBrightness();
				final double targetBrightness = getBrightnessSampler().sampleBrightness(level, player,
						getPositionResolver().resolvePosition(player)) * (leastBrightness - mostBrightness) +
						mostBrightness;

				client.options.gamma = getTuneMethod().tune(client.options.gamma, targetBrightness,
						getConfig()::getTuneMultiplier);
			} else {
				client.options.gamma = config.getDefaultBrightness();
			}
		});
	}

	public BrightnessAutoTuneClientMod saveConfig() {
		try (final BufferedWriter out = Files.newBufferedWriter(getConfigPath())) {
			getGson().toJson(getConfig(), out);
		} catch (final IOException ioe) {
			getLogger().error("Config save exception.", ioe);
		}

		return this;
	}

	public BrightnessAutoTuneClientMod setBrightnessSampler(final BrightnessSampler brightnessSampler) {
		this.brightnessSampler = nullable(brightnessSampler, BrightnessSampler.LEGACY);

		return this;
	}

	public BrightnessAutoTuneClientMod setConfig(final @Nonnull Config config) {
		this.config = nonnull(config);

		return this;
	}

	public BrightnessAutoTuneClientMod setGson(final @Nonnull Gson gson) {
		this.gson = nonnull(gson);

		return this;
	}

	public BrightnessAutoTuneClientMod setPositionResolver(final PlayerPositionResolver positionResolver) {
		this.positionResolver = nullable(positionResolver, PlayerPositionResolver.LEGACY);

		return this;
	}

	public BrightnessAutoTuneClientMod setTuneMethod(final TuneMethod tuneMethod) {
		this.tuneMethod = nullable(tuneMethod, TuneMethod.LEGACY);

		return this;
	}
}
