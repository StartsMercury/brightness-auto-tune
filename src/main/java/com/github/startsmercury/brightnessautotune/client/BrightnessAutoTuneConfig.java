package com.github.startsmercury.brightnessautotune.client;

import static com.github.startsmercury.brightnessautotune.util.Clamp.clamp;
import static net.fabricmc.api.EnvType.CLIENT;

import java.io.Serial;
import java.io.Serializable;

import net.fabricmc.api.Environment;

@Environment(CLIENT)
public class BrightnessAutoTuneConfig implements Cloneable, Serializable {
	@Serial
	private static final long serialVersionUID = -1252132672103122771L;

	private double brightness;

	private double defaultBrightness;

	private double leastBrightness;

	private double mostBrightness;

	private boolean tuneBrightness;

	private double tuneMultiplier;

	public BrightnessAutoTuneConfig() {
		this(true);
	}

	public BrightnessAutoTuneConfig(final boolean tuneBrightness) {
		this(tuneBrightness, 0.08D);
	}

	public BrightnessAutoTuneConfig(final boolean tuneBrightness, final double tuneMultiplier) {
		this(tuneBrightness, tuneMultiplier, 0.5D);
	}

	public BrightnessAutoTuneConfig(final boolean tuneBrightness, final double tuneMultiplier,
			final double defaultBrightness) {
		this(tuneBrightness, tuneMultiplier, defaultBrightness, 0.0D, 0.5D);
	}

	public BrightnessAutoTuneConfig(final boolean tuneBrightness, final double tuneMultiplier,
			final double defaultBrightness, final double leastBrightness, final double mostBrightness) {
		this.brightness = 0.5D;
		this.defaultBrightness = clamp(0.0D, defaultBrightness, 1.0D);
		this.leastBrightness = clamp(0.0D, leastBrightness, 1.0D);
		this.mostBrightness = clamp(0.0D, mostBrightness, 1.0D);
		this.tuneBrightness = tuneBrightness;
		this.tuneMultiplier = clamp(0.0D, tuneMultiplier, 1.0D);
	}

	public BrightnessAutoTuneConfig(final BrightnessAutoTuneConfig other) {
		this(other.shouldTuneBrightness(), other.getTuneMultiplier(), other.getDefaultBrightness(),
				other.getLeastBrightness(), other.getMostBrightness());
	}

	@Override
	public BrightnessAutoTuneConfig clone() {
		try {
			return ((BrightnessAutoTuneConfig) super.clone()).setBrightness(0.5D);
		} catch (final CloneNotSupportedException cnse) {
			throw new InternalError(cnse);
		}
	}

	public BrightnessAutoTuneConfig copy(final BrightnessAutoTuneConfig other) {
		// @formatter:off
		return this.setDefaultBrightness(other.getDefaultBrightness())
		           .setLeastBrightness(other.getLeastBrightness())
		           .setMostBrightness(other.getMostBrightness())
		           .setTuneBrightness(other.shouldTuneBrightness())
		           .setTuneMultiplier(other.getTuneMultiplier());
		// @formatter:on
	}

	protected boolean equals(final BrightnessAutoTuneConfig other) {
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
		return this == obj ? true : obj instanceof final BrightnessAutoTuneConfig other ? equals(other) : false;
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

	public BrightnessAutoTuneConfig setBrightness(final double brightness) {
		this.brightness = clamp(0.0D, brightness, 1.0D);

		return this;
	}

	public BrightnessAutoTuneConfig setDefaultBrightness(final double defaultBrightness) {
		this.defaultBrightness = defaultBrightness;

		return this;
	}

	public BrightnessAutoTuneConfig setLeastBrightness(final double leastBrightness) {
		this.leastBrightness = clamp(0.0D, leastBrightness, 1.0D);

		return this;
	}

	public BrightnessAutoTuneConfig setMostBrightness(final double mostBrightness) {
		this.mostBrightness = clamp(0.0D, mostBrightness, 1.0D);

		return this;
	}

	public BrightnessAutoTuneConfig setTuneBrightness(final boolean tuneBrightness) {
		this.tuneBrightness = tuneBrightness;

		return this;
	}

	public BrightnessAutoTuneConfig setTuneMultiplier(final double tuneMultiplier) {
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