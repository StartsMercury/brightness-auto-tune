package com.github.startsmercury.brightnessautotune.entrypoint;

import static com.github.startsmercury.brightnessautotune.util.Nonnull.nonnull;
import static com.github.startsmercury.brightnessautotune.util.Nullable.nullable;
import static java.lang.Runtime.getRuntime;
import static net.fabricmc.api.EnvType.CLIENT;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.startsmercury.brightnessautotune.api.BrightnessSampler;
import com.github.startsmercury.brightnessautotune.api.PlayerPositionResolver;
import com.github.startsmercury.brightnessautotune.api.TuneMethod;
import com.github.startsmercury.brightnessautotune.client.BrightnessAutoTuneConfig;
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
	private static @Nullable BrightnessAutoTuneClientMod instance = null;

	public static @Nonnull BrightnessAutoTuneClientMod getInstance() {
		if (instance == null) {
			throw new IllegalStateException("Accessed Brightness Auto Tune (Client) too early!");
		} else {
			return instance;
		}
	}

	private @Nonnull BrightnessSampler brightnessSampler;

	private @Nonnull BrightnessAutoTuneConfig brightnessAutoTuneConfig;

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

	public @Nonnull BrightnessAutoTuneConfig getConfig() {
		return this.brightnessAutoTuneConfig;
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
			setConfig(getGson().fromJson(in, BrightnessAutoTuneConfig.class));
		} catch (final IOException | NullPointerException e) {
			getLogger().error("Config load exception.", e);

			setConfig(new BrightnessAutoTuneConfig());
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
			final BrightnessAutoTuneConfig brightnessAutoTuneConfig = getConfig();

			if (!brightnessAutoTuneConfig.shouldTuneBrightness()) {
				return;
			}

			final Minecraft client = Minecraft.getInstance();
			final LocalPlayer player = client.player;

			if (player != null) {
				final double leastBrightness = brightnessAutoTuneConfig.getLeastBrightness();
				final double mostBrightness = brightnessAutoTuneConfig.getMostBrightness();
				final double targetBrightness = getBrightnessSampler().sampleBrightness(level, player,
						getPositionResolver().resolvePosition(player)) * (leastBrightness - mostBrightness) +
						mostBrightness;

				client.options.gamma = getTuneMethod().tune(client.options.gamma, targetBrightness,
						getConfig()::getTuneMultiplier);
			} else {
				client.options.gamma = brightnessAutoTuneConfig.getDefaultBrightness();
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

	public BrightnessAutoTuneClientMod setConfig(final @Nonnull BrightnessAutoTuneConfig brightnessAutoTuneConfig) {
		this.brightnessAutoTuneConfig = nonnull(brightnessAutoTuneConfig);

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
