package com.github.startsmercury.brightnessautotune.mixin.sodium;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.binding.OptionBinding;

@Mixin(OptionImpl.class)
interface OptionImplMixin<S, T> {
	@Accessor(value = "binding", remap = false)
	@Mutable
	void setBinding(OptionBinding<S, T> binding);
}
