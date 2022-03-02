package com.github.startsmercury.brightnessautotune.mixin.minecraft;

import static net.fabricmc.api.EnvType.CLIENT;

import java.util.function.BiConsumer;
import java.util.function.Function;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.fabricmc.api.Environment;
import net.minecraft.client.Options;
import net.minecraft.client.ProgressOption;

@Environment(CLIENT)
@Mixin(ProgressOption.class)
interface ProgressOptionMixin {
	@Accessor("getter")
	@Mutable
	void setGetter(Function<Options, Double> getter);

	@Accessor("setter")
	@Mutable
	void setSetter(BiConsumer<Options, Double> setter);
}
