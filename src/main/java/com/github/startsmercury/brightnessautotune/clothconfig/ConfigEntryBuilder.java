package com.github.startsmercury.brightnessautotune.clothconfig;

import static com.github.startsmercury.brightnessautotune.util.FormattedTextMessages.translatable;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.mojang.blaze3d.platform.InputConstants.Key;

import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceList;
import me.shedaniel.clothconfig2.api.AbstractConfigListEntry;
import me.shedaniel.clothconfig2.api.ModifierKeyCode;
import me.shedaniel.clothconfig2.gui.entries.DropdownBoxEntry.SelectionCellCreator;
import me.shedaniel.clothconfig2.gui.entries.DropdownBoxEntry.SelectionTopCellElement;
import me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder;
import me.shedaniel.clothconfig2.impl.builders.ColorFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.DoubleFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.DoubleListBuilder;
import me.shedaniel.clothconfig2.impl.builders.DropdownMenuBuilder;
import me.shedaniel.clothconfig2.impl.builders.EnumSelectorBuilder;
import me.shedaniel.clothconfig2.impl.builders.FloatFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.FloatListBuilder;
import me.shedaniel.clothconfig2.impl.builders.IntFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.IntListBuilder;
import me.shedaniel.clothconfig2.impl.builders.IntSliderBuilder;
import me.shedaniel.clothconfig2.impl.builders.KeyCodeBuilder;
import me.shedaniel.clothconfig2.impl.builders.LongFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.LongListBuilder;
import me.shedaniel.clothconfig2.impl.builders.LongSliderBuilder;
import me.shedaniel.clothconfig2.impl.builders.SelectorBuilder;
import me.shedaniel.clothconfig2.impl.builders.StringFieldBuilder;
import me.shedaniel.clothconfig2.impl.builders.StringListBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import me.shedaniel.clothconfig2.impl.builders.TextDescriptionBuilder;
import me.shedaniel.clothconfig2.impl.builders.TextFieldBuilder;
import me.shedaniel.math.Color;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;

public class ConfigEntryBuilder implements me.shedaniel.clothconfig2.api.ConfigEntryBuilder {
	private static final ReferenceList<Component> appendTooltip(final ReferenceList<Component> tooltip,
			final String key) {
		for (int i = 0; i >= 0; i++) {
			final String tooltipLineKey = key + ".tooltip." + i;
			final Component tooltipLine = translatable(tooltipLineKey);

			if (!tooltipLineKey.equals(tooltipLine.getString())) {
				tooltip.add(tooltipLine);
			} else {
				break;
			}
		}

		return tooltip;
	}

	private static final ReferenceList<Component> createTooltip(final String key) {
		return appendTooltip(new ReferenceArrayList<Component>(), key);
	}

	private static final Optional<Component[]> createTooltip(final String key, final Object value) {
		final ReferenceList<Component> tooltip = createTooltip(key + '.' + value);

		if (tooltip.isEmpty()) {
			appendTooltip(tooltip, key);
		}

		return Optional.of(tooltip.toArray(new Component[tooltip.size()]));
	}

	private final me.shedaniel.clothconfig2.api.ConfigEntryBuilder delegate;

	private final String key;

	public ConfigEntryBuilder(final me.shedaniel.clothconfig2.api.ConfigEntryBuilder delegate, final String key) {
		this.delegate = delegate;
		this.key = key;
	}

	@Override
	public KeyCodeBuilder fillKeybindingField(final Component fieldNameKey, final KeyMapping value) {
		return this.delegate.fillKeybindingField(fieldNameKey, value);
	}

	@Override
	public Component getResetButtonKey() {
		return this.delegate.getResetButtonKey();
	}

	@Override
	public ConfigEntryBuilder setResetButtonKey(final Component p0) {
		this.delegate.setResetButtonKey(p0);

		return this;
	}

	@Override
	public ColorFieldBuilder startAlphaColorField(final Component fieldNameKey, final Color color) {
		return this.delegate.startAlphaColorField(fieldNameKey, color);
	}

	@Override
	public ColorFieldBuilder startAlphaColorField(final Component fieldNameKey, final int value) {
		return this.delegate.startAlphaColorField(fieldNameKey, value);
	}

	public ColorFieldBuilder startAlphaColorField(final String fieldNameKey, final Color color) {
		return this.delegate.startAlphaColorField(translatable(this.key + '.' + fieldNameKey), color);
	}

	public ColorFieldBuilder startAlphaColorField(final String fieldNameKey, final int value) {
		return this.delegate.startAlphaColorField(translatable(this.key + '.' + fieldNameKey), value);
	}

	@Override
	public BooleanToggleBuilder startBooleanToggle(final Component p0, final boolean p1) {
		return this.delegate.startBooleanToggle(p0, p1);
	}

	public BooleanToggleBuilder startBooleanToggle(final String fieldNameKey, final boolean p1) {
		final String key = this.key + '.' + fieldNameKey;
		final BooleanToggleBuilder builder = this.delegate.startBooleanToggle(translatable(key), p1);

		builder.setTooltipSupplier(value -> createTooltip(key, value));

		return builder;
	}

	@Override
	public ColorFieldBuilder startColorField(final Component fieldNameKey, final Color color) {
		return this.delegate.startColorField(fieldNameKey, color);
	}

	@Override
	public ColorFieldBuilder startColorField(final Component p0, final int p1) {
		return this.delegate.startColorField(p0, p1);
	}

	@Override
	public ColorFieldBuilder startColorField(final Component fieldNameKey, final TextColor color) {
		return this.delegate.startColorField(fieldNameKey, color);
	}

	public ColorFieldBuilder startColorField(final String fieldNameKey, final Color color) {
		return this.delegate.startColorField(translatable(this.key + '.' + fieldNameKey), color);
	}

	public ColorFieldBuilder startColorField(final String fieldNameKey, final int p1) {
		return this.delegate.startColorField(translatable(this.key + '.' + fieldNameKey), p1);
	}

	public ColorFieldBuilder startColorField(final String fieldNameKey, final TextColor color) {
		return this.delegate.startColorField(translatable(this.key + '.' + fieldNameKey), color);
	}

	@Override
	public DoubleFieldBuilder startDoubleField(final Component p0, final double p1) {
		return this.delegate.startDoubleField(p0, p1);
	}

	public DoubleFieldBuilder startDoubleField(final String fieldNameKey, final double p1) {
		final String key = this.key + '.' + fieldNameKey;
		final DoubleFieldBuilder builder = this.delegate.startDoubleField(translatable(key), p1);

		builder.setTooltipSupplier(value -> createTooltip(key, value));

		return builder;
	}

	@Override
	public DoubleListBuilder startDoubleList(final Component p0, final List<Double> p1) {
		return this.delegate.startDoubleList(p0, p1);
	}

	public DoubleListBuilder startDoubleList(final String fieldNameKey, final List<Double> p1) {
		return this.delegate.startDoubleList(translatable(this.key + '.' + fieldNameKey), p1);
	}

	@Override
	public <T> DropdownMenuBuilder<T> startDropdownMenu(final Component fieldNameKey,
			final SelectionTopCellElement<T> topCellElement) {
		return this.delegate.startDropdownMenu(fieldNameKey, topCellElement);
	}

	@Override
	public <T> DropdownMenuBuilder<T> startDropdownMenu(final Component p0, final SelectionTopCellElement<T> p1,
			final SelectionCellCreator<T> p2) {
		return this.delegate.startDropdownMenu(p0, p1, p2);
	}

	@Override
	public <T> DropdownMenuBuilder<T> startDropdownMenu(final Component fieldNameKey, final T value,
			final Function<String, T> toObjectFunction) {
		return this.delegate.startDropdownMenu(fieldNameKey, value, toObjectFunction);
	}

	@Override
	public <T> DropdownMenuBuilder<T> startDropdownMenu(final Component fieldNameKey, final T value,
			final Function<String, T> toObjectFunction, final Function<T, Component> toTextFunction) {
		return this.delegate.startDropdownMenu(fieldNameKey, value, toObjectFunction, toTextFunction);
	}

	@Override
	public <T> DropdownMenuBuilder<T> startDropdownMenu(final Component fieldNameKey, final T value,
			final Function<String, T> toObjectFunction, final Function<T, Component> toTextFunction,
			final SelectionCellCreator<T> cellCreator) {
		return this.delegate.startDropdownMenu(fieldNameKey, value, toObjectFunction, toTextFunction, cellCreator);
	}

	@Override
	public <T> DropdownMenuBuilder<T> startDropdownMenu(final Component fieldNameKey, final T value,
			final Function<String, T> toObjectFunction, final SelectionCellCreator<T> cellCreator) {
		return this.delegate.startDropdownMenu(fieldNameKey, value, toObjectFunction, cellCreator);
	}

	public <T> DropdownMenuBuilder<T> startDropdownMenu(final String fieldNameKey,
			final SelectionTopCellElement<T> topCellElement) {
		return this.delegate.startDropdownMenu(translatable(this.key + '.' + fieldNameKey), topCellElement);
	}

	public <T> DropdownMenuBuilder<T> startDropdownMenu(final String fieldNameKey, final SelectionTopCellElement<T> p1,
			final SelectionCellCreator<T> p2) {
		return this.delegate.startDropdownMenu(translatable(this.key + '.' + fieldNameKey), p1, p2);
	}

	public <T> DropdownMenuBuilder<T> startDropdownMenu(final String fieldNameKey, final T value,
			final Function<String, T> toObjectFunction) {
		return this.delegate.startDropdownMenu(translatable(this.key + '.' + fieldNameKey), value, toObjectFunction);
	}

	public <T> DropdownMenuBuilder<T> startDropdownMenu(final String fieldNameKey, final T value,
			final Function<String, T> toObjectFunction, final Function<T, Component> toTextFunction) {
		return this.delegate.startDropdownMenu(translatable(this.key + '.' + fieldNameKey), value, toObjectFunction,
				toTextFunction);
	}

	public <T> DropdownMenuBuilder<T> startDropdownMenu(final String fieldNameKey, final T value,
			final Function<String, T> toObjectFunction, final Function<T, Component> toTextFunction,
			final SelectionCellCreator<T> cellCreator) {
		return this.delegate.startDropdownMenu(translatable(this.key + '.' + fieldNameKey), value, toObjectFunction,
				toTextFunction, cellCreator);
	}

	public <T> DropdownMenuBuilder<T> startDropdownMenu(final String fieldNameKey, final T value,
			final Function<String, T> toObjectFunction, final SelectionCellCreator<T> cellCreator) {
		return this.delegate.startDropdownMenu(translatable(this.key + '.' + fieldNameKey), value, toObjectFunction,
				cellCreator);
	}

	@Override
	public <T extends Enum<?>> EnumSelectorBuilder<T> startEnumSelector(final Component p0, final Class<T> p1,
			final T p2) {
		return this.delegate.startEnumSelector(p0, p1, p2);
	}

	public <T extends Enum<?>> EnumSelectorBuilder<T> startEnumSelector(final String fieldNameKey, final Class<T> p1,
			final T p2) {
		return this.delegate.startEnumSelector(translatable(this.key + '.' + fieldNameKey), p1, p2);
	}

	@Override
	public FloatFieldBuilder startFloatField(final Component p0, final float p1) {
		return this.delegate.startFloatField(p0, p1);
	}

	public FloatFieldBuilder startFloatField(final String fieldNameKey, final float p1) {
		return this.delegate.startFloatField(translatable(this.key + '.' + fieldNameKey), p1);
	}

	@Override
	public FloatListBuilder startFloatList(final Component p0, final List<Float> p1) {
		return this.delegate.startFloatList(p0, p1);
	}

	public FloatListBuilder startFloatList(final String fieldNameKey, final List<Float> p1) {
		return this.delegate.startFloatList(translatable(this.key + '.' + fieldNameKey), p1);
	}

	@Override
	public IntFieldBuilder startIntField(final Component p0, final int p1) {
		return this.delegate.startIntField(p0, p1);
	}

	public IntFieldBuilder startIntField(final String fieldNameKey, final int p1) {
		return this.delegate.startIntField(translatable(this.key + '.' + fieldNameKey), p1);
	}

	@Override
	public IntListBuilder startIntList(final Component p0, final List<Integer> p1) {
		return this.delegate.startIntList(p0, p1);
	}

	public IntListBuilder startIntList(final String fieldNameKey, final List<Integer> p1) {
		return this.delegate.startIntList(translatable(this.key + '.' + fieldNameKey), p1);
	}

	@Override
	public IntSliderBuilder startIntSlider(final Component p0, final int p1, final int p2, final int p3) {
		return this.delegate.startIntSlider(p0, p1, p2, p3);
	}

	public IntSliderBuilder startIntSlider(final String fieldNameKey, final int p1, final int p2, final int p3) {
		final String key = this.key + '.' + fieldNameKey;
		final IntSliderBuilder builder = this.delegate.startIntSlider(translatable(key), p1, p2, p3);

		builder.setTooltipSupplier(value -> createTooltip(key, value));

		return builder;
	}

	@Override
	public KeyCodeBuilder startKeyCodeField(final Component fieldNameKey, final Key value) {
		return this.delegate.startKeyCodeField(fieldNameKey, value);
	}

	public KeyCodeBuilder startKeyCodeField(final String fieldNameKey, final Key value) {
		return this.delegate.startKeyCodeField(translatable(this.key + '.' + fieldNameKey), value);
	}

	@Override
	public LongFieldBuilder startLongField(final Component p0, final long p1) {
		return this.delegate.startLongField(p0, p1);
	}

	public LongFieldBuilder startLongField(final String fieldNameKey, final long p1) {
		return this.delegate.startLongField(translatable(this.key + '.' + fieldNameKey), p1);
	}

	@Override
	public LongListBuilder startLongList(final Component p0, final List<Long> p1) {
		return this.delegate.startLongList(p0, p1);
	}

	public LongListBuilder startLongList(final String fieldNameKey, final List<Long> p1) {
		return this.delegate.startLongList(translatable(this.key + '.' + fieldNameKey), p1);
	}

	@Override
	public LongSliderBuilder startLongSlider(final Component p0, final long p1, final long p2, final long p3) {
		return this.delegate.startLongSlider(p0, p1, p2, p3);
	}

	public LongSliderBuilder startLongSlider(final String fieldNameKey, final long p1, final long p2, final long p3) {
		return this.delegate.startLongSlider(translatable(this.key + '.' + fieldNameKey), p1, p2, p3);
	}

	@Override
	public KeyCodeBuilder startModifierKeyCodeField(final Component p0, final ModifierKeyCode p1) {
		return this.delegate.startModifierKeyCodeField(p0, p1);
	}

	public KeyCodeBuilder startModifierKeyCodeField(final String fieldNameKey, final ModifierKeyCode p1) {
		return this.delegate.startModifierKeyCodeField(translatable(this.key + '.' + fieldNameKey), p1);
	}

	@Override
	public <T> SelectorBuilder<T> startSelector(final Component p0, final T[] p1, final T p2) {
		return this.delegate.startSelector(p0, p1, p2);
	}

	public <T> SelectorBuilder<T> startSelector(final String fieldNameKey, final T[] p1, final T p2) {
		return this.delegate.startSelector(translatable(this.key + '.' + fieldNameKey), p1, p2);
	}

	@Override
	public StringFieldBuilder startStrField(final Component p0, final String p1) {
		return this.delegate.startStrField(p0, p1);
	}

	public StringFieldBuilder startStrField(final String fieldNameKey, final String p1) {
		return this.delegate.startStrField(translatable(this.key + '.' + fieldNameKey), p1);
	}

	@Override
	public DropdownMenuBuilder<String> startStringDropdownMenu(final Component fieldNameKey, final String value) {
		return this.delegate.startStringDropdownMenu(fieldNameKey, value);
	}

	@Override
	public DropdownMenuBuilder<String> startStringDropdownMenu(final Component fieldNameKey, final String value,
			final Function<String, Component> toTextFunction) {
		return this.delegate.startStringDropdownMenu(fieldNameKey, value, toTextFunction);
	}

	@Override
	public DropdownMenuBuilder<String> startStringDropdownMenu(final Component fieldNameKey, final String value,
			final Function<String, Component> toTextFunction, final SelectionCellCreator<String> cellCreator) {
		return this.delegate.startStringDropdownMenu(fieldNameKey, value, toTextFunction, cellCreator);
	}

	@Override
	public DropdownMenuBuilder<String> startStringDropdownMenu(final Component fieldNameKey, final String value,
			final SelectionCellCreator<String> cellCreator) {
		return this.delegate.startStringDropdownMenu(fieldNameKey, value, cellCreator);
	}

	public DropdownMenuBuilder<String> startStringDropdownMenu(final String fieldNameKey, final String value) {
		return this.delegate.startStringDropdownMenu(translatable(this.key + '.' + fieldNameKey), value);
	}

	public DropdownMenuBuilder<String> startStringDropdownMenu(final String fieldNameKey, final String value,
			final Function<String, Component> toTextFunction) {
		return this.delegate.startStringDropdownMenu(translatable(this.key + '.' + fieldNameKey), value,
				toTextFunction);
	}

	public DropdownMenuBuilder<String> startStringDropdownMenu(final String fieldNameKey, final String value,
			final Function<String, Component> toTextFunction, final SelectionCellCreator<String> cellCreator) {
		return this.delegate.startStringDropdownMenu(translatable(this.key + '.' + fieldNameKey), value, toTextFunction,
				cellCreator);
	}

	public DropdownMenuBuilder<String> startStringDropdownMenu(final String fieldNameKey, final String value,
			final SelectionCellCreator<String> cellCreator) {
		return this.delegate.startStringDropdownMenu(translatable(this.key + '.' + fieldNameKey), value, cellCreator);
	}

	@Override
	public StringListBuilder startStrList(final Component p0, final List<String> p1) {
		return this.delegate.startStrList(p0, p1);
	}

	public StringListBuilder startStrList(final String fieldNameKey, final List<String> p1) {
		return this.delegate.startStrList(translatable(this.key + '.' + fieldNameKey), p1);
	}

	@Override
	public SubCategoryBuilder startSubCategory(final Component p0) {
		return this.delegate.startSubCategory(p0);
	}

	@Override
	public SubCategoryBuilder startSubCategory(final Component p0,
			@SuppressWarnings("rawtypes") final List<AbstractConfigListEntry> p1) {
		return this.delegate.startSubCategory(p0, p1);
	}

	public SubCategoryBuilder startSubCategory(final String fieldNameKey) {
		return this.delegate.startSubCategory(translatable(this.key + '.' + fieldNameKey));
	}

	public SubCategoryBuilder startSubCategory(final String fieldNameKey,
			@SuppressWarnings("rawtypes") final List<AbstractConfigListEntry> p1) {
		return this.delegate.startSubCategory(translatable(this.key + '.' + fieldNameKey), p1);
	}

	@Override
	public TextDescriptionBuilder startTextDescription(final Component p0) {
		return this.delegate.startTextDescription(p0);
	}

	public TextDescriptionBuilder startTextDescription(final String fieldNameKey) {
		return this.delegate.startTextDescription(translatable(this.key + '.' + fieldNameKey));
	}

	@Override
	public TextFieldBuilder startTextField(final Component p0, final String p1) {
		return this.delegate.startTextField(p0, p1);
	}

	public TextFieldBuilder startTextField(final String fieldNameKey, final String p1) {
		return this.delegate.startTextField(translatable(this.key + '.' + fieldNameKey), p1);
	}
}
