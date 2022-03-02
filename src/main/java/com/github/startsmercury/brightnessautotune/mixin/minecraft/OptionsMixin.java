package com.github.startsmercury.brightnessautotune.mixin.minecraft;

import static net.fabricmc.api.EnvType.CLIENT;
import static org.objectweb.asm.Opcodes.GETFIELD;
import static org.objectweb.asm.Opcodes.PUTFIELD;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.github.startsmercury.brightnessautotune.entrypoint.BrightnessAutoTuneClientMod;

import net.fabricmc.api.Environment;
import net.minecraft.client.Options;

@Environment(CLIENT)
@Mixin(Options.class)
abstract class OptionsMixin {
	private OptionsMixin() {
	}

	@Redirect(method = "processOptions(Lnet/minecraft/client/Options$FieldAccess;)V",
			at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;gamma:D", opcode = GETFIELD))
	private final double redirectLoadGamma(final Options it) {
		return BrightnessAutoTuneClientMod.getInstance().getConfig().getBrightness();
	}

	@Redirect(method = "processOptions(Lnet/minecraft/client/Options$FieldAccess;)V",
			at = @At(value = "FIELD", target = "Lnet/minecraft/client/Options;gamma:D", opcode = PUTFIELD))
	private final void redirectSaveGamma(final Options it, final double gamma) {
		BrightnessAutoTuneClientMod.getInstance().getConfig().setBrightness(gamma);
	}
}
