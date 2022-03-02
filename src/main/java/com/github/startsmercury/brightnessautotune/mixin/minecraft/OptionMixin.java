package com.github.startsmercury.brightnessautotune.mixin.minecraft;

import static net.fabricmc.api.EnvType.CLIENT;
import static net.minecraft.client.Option.GAMMA;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.startsmercury.brightnessautotune.entrypoint.BrightnessAutoTuneClientMod;

import net.fabricmc.api.Environment;
import net.minecraft.client.Option;

@Environment(CLIENT)
@Mixin(Option.class)
abstract class OptionMixin {
	@Inject(method = "<clinit>", at = @At("RETURN"))
	private static final void redirectGammaGetterAndSetter(final CallbackInfo callback) {
		final ProgressOptionMixin gamma = (ProgressOptionMixin) GAMMA;

		gamma.setGetter(options -> BrightnessAutoTuneClientMod.getInstance().getConfig().getBrightness());
		gamma.setSetter((options, value) -> BrightnessAutoTuneClientMod.getInstance().getConfig().setBrightness(value));
	}

	private OptionMixin() {
	}
}
