package com.github.startsmercury.brightnessautotune.clothconfig;

import static com.github.startsmercury.brightnessautotune.util.FormattedTextMessages.translatable;

import java.util.function.Consumer;

import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.ConfigEntryBuilderImpl;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ConfigBuilder implements me.shedaniel.clothconfig2.api.ConfigBuilder {
	private final me.shedaniel.clothconfig2.api.ConfigBuilder delegate;

	private final String key;

	public ConfigBuilder(final String key) {
		this.delegate = me.shedaniel.clothconfig2.api.ConfigBuilder.create();
		this.key = key;

		setTitle(translatable(key + ".title"));
	}

	@Override
	public ConfigBuilder alwaysShowTabs() {
		this.delegate.alwaysShowTabs();

		return this;
	}

	@Override
	public Screen build() {
		return this.delegate.build();
	}

	@Override
	public boolean doesConfirmSave() {
		return this.delegate.doesConfirmSave();
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean doesProcessErrors() {
		return this.delegate.doesProcessErrors();
	}

	@Override
	public ConfigEntryBuilder entryBuilder() {
		return this.delegate.entryBuilder();
	}

	@Override
	public Consumer<Screen> getAfterInitConsumer() {
		return this.delegate.getAfterInitConsumer();
	}

	@Override
	public ResourceLocation getDefaultBackgroundTexture() {
		return this.delegate.getDefaultBackgroundTexture();
	}

	@Override
	@SuppressWarnings("deprecation")
	public ConfigEntryBuilderImpl getEntryBuilder() {
		return this.delegate.getEntryBuilder();
	}

	public String getKey() {
		return this.key;
	}

	@Override
	public me.shedaniel.clothconfig2.api.ConfigCategory getOrCreateCategory(final Component p0) {
		return this.delegate.getOrCreateCategory(p0);
	}

	public ConfigCategory getOrCreateCategory(final String categoryKey) {
		final String key = this.key + '.' + categoryKey;

		return new ConfigCategory(getOrCreateCategory(translatable(key)), key);
	}

	@Override
	public Screen getParentScreen() {
		return this.delegate.getParentScreen();
	}

	@Override
	public Runnable getSavingRunnable() {
		return this.delegate.getSavingRunnable();
	}

	@Override
	public Component getTitle() {
		return this.delegate.getTitle();
	}

	@Override
	public boolean hasCategory(final Component p0) {
		return this.delegate.hasCategory(p0);
	}

	@Override
	public boolean hasTransparentBackground() {
		return this.delegate.hasTransparentBackground();
	}

	@Override
	public boolean isAlwaysShowTabs() {
		return this.delegate.isAlwaysShowTabs();
	}

	@Override
	public boolean isEditable() {
		return this.delegate.isEditable();
	}

	@Override
	public boolean isListSmoothScrolling() {
		return this.delegate.isListSmoothScrolling();
	}

	@Override
	public boolean isTabsSmoothScrolling() {
		return this.delegate.isTabsSmoothScrolling();
	}

	@Override
	public ConfigBuilder removeCategory(final Component p0) {
		this.delegate.removeCategory(p0);

		return this;
	}

	@Override
	public ConfigBuilder removeCategoryIfExists(final Component p0) {
		this.delegate.removeCategoryIfExists(p0);

		return this;
	}

	@Override
	public ConfigBuilder setAfterInitConsumer(final Consumer<Screen> p0) {
		this.delegate.setAfterInitConsumer(p0);

		return this;
	}

	@Override
	public ConfigBuilder setAlwaysShowTabs(final boolean p0) {
		this.delegate.setAlwaysShowTabs(p0);

		return this;
	}

	@Override
	public ConfigBuilder setDefaultBackgroundTexture(final ResourceLocation p0) {
		this.delegate.setDefaultBackgroundTexture(p0);

		return this;
	}

	@Override
	public ConfigBuilder setDoesConfirmSave(final boolean p0) {
		this.delegate.setDoesConfirmSave(p0);

		return this;
	}

	@Override
	@SuppressWarnings("deprecation")
	public ConfigBuilder setDoesProcessErrors(final boolean processErrors) {
		this.delegate.setDoesProcessErrors(processErrors);

		return this;
	}

	@Override
	public ConfigBuilder setEditable(final boolean p0) {
		this.delegate.setEditable(p0);

		return this;
	}

	@Override
	public ConfigBuilder setFallbackCategory(final me.shedaniel.clothconfig2.api.ConfigCategory p0) {
		this.delegate.setFallbackCategory(p0);

		return this;
	}

	@Override
	public void setGlobalized(final boolean p0) {
		this.delegate.setGlobalized(p0);
	}

	@Override
	public void setGlobalizedExpanded(final boolean p0) {
		this.delegate.setGlobalizedExpanded(p0);
	}

	@Override
	public ConfigBuilder setParentScreen(final Screen p0) {
		this.delegate.setParentScreen(p0);

		return this;
	}

	@Override
	public ConfigBuilder setSavingRunnable(final Runnable p0) {
		this.delegate.setSavingRunnable(p0);

		return this;
	}

	@Override
	public ConfigBuilder setShouldListSmoothScroll(final boolean p0) {
		this.delegate.setShouldListSmoothScroll(p0);

		return this;
	}

	@Override
	public ConfigBuilder setShouldTabsSmoothScroll(final boolean p0) {
		this.delegate.setShouldTabsSmoothScroll(p0);

		return this;
	}

	@Override
	public ConfigBuilder setTitle(final Component p0) {
		this.delegate.setTitle(p0);

		return this;
	}

	@Override
	public ConfigBuilder setTransparentBackground(final boolean p0) {
		this.delegate.setTransparentBackground(p0);

		return this;
	}

	@Override
	public ConfigBuilder solidBackground() {
		this.delegate.solidBackground();

		return this;
	}

	@Override
	public ConfigBuilder transparentBackground() {
		this.delegate.transparentBackground();

		return this;
	}
}
