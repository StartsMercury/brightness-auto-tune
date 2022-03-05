package com.github.startsmercury.brightnessautotune.clothconfig;

import static com.github.startsmercury.brightnessautotune.util.FormattedTextMessages.translatable;
import static net.fabricmc.api.EnvType.CLIENT;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceList;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;

@Environment(CLIENT)
public class ConfigCategory implements me.shedaniel.clothconfig2.api.ConfigCategory {
	private final me.shedaniel.clothconfig2.api.ConfigCategory delegate;

	private final String key;

	public ConfigCategory(final me.shedaniel.clothconfig2.api.ConfigCategory delegate, final String key) {
		this.delegate = delegate;
		this.key = key;

		setDescription(() -> {
			final ReferenceList<FormattedText> description = new ReferenceArrayList<>();

			for (int i = 0; i >= 0; i++) {
				final String descriptionLineKey = this.key + '.' + i;
				final FormattedText descriptionLine = translatable(descriptionLineKey);

				System.out.println(descriptionLine.getString());

				if (!descriptionLineKey.equals(descriptionLine.getString())) {
					description.add(descriptionLine);
				} else {
					break;
				}
			}

			return Optional.of(description.toArray(new FormattedText[description.size()]));
		});
	}

	@Override
	public ConfigCategory addEntry(@SuppressWarnings("rawtypes") final AbstractConfigListEntry p0) {
		this.delegate.addEntry(p0);

		return this;
	}

	public ConfigEntryBuilder entryBuilder() {
		return new ConfigEntryBuilder(me.shedaniel.clothconfig2.api.ConfigEntryBuilder.create(), this.key);
	}

	@Override
	public @Nullable ResourceLocation getBackground() {
		return this.delegate.getBackground();
	}

	@Override
	public Component getCategoryKey() {
		return this.delegate.getCategoryKey();
	}

	@Override
	public @Nullable Supplier<Optional<FormattedText[]>> getDescription() {
		return this.delegate.getDescription();
	}

	@Override
	@SuppressWarnings("deprecation")
	public List<Object> getEntries() {
		return this.delegate.getEntries();
	}

	public String getKey() {
		return this.key;
	}

	@Override
	public void removeCategory() {
		this.delegate.removeCategory();
	}

	@Override
	public void setBackground(@Nullable final ResourceLocation p0) {
		this.delegate.setBackground(p0);
	}

	@Override
	public ConfigCategory setCategoryBackground(final ResourceLocation p0) {
		this.delegate.setCategoryBackground(p0);

		return this;
	}

	@Override
	public void setDescription(@Nullable final FormattedText[] description) {
		this.delegate.setDescription(description);
	}

	@Override
	public void setDescription(@Nullable final Supplier<Optional<FormattedText[]>> p0) {
		this.delegate.setDescription(p0);
	}
}
