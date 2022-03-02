package com.github.startsmercury.brightnessautotune.mixin.sodium;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.startsmercury.brightnessautotune.entrypoint.BrightnessAutoTuneClientMod;

import me.jellysquid.mods.sodium.client.gui.SodiumGameOptionPages;
import me.jellysquid.mods.sodium.client.gui.options.Option;
import me.jellysquid.mods.sodium.client.gui.options.OptionGroup;
import me.jellysquid.mods.sodium.client.gui.options.OptionImpl;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import me.jellysquid.mods.sodium.client.gui.options.binding.GenericBinding;
import me.jellysquid.mods.sodium.client.gui.options.storage.MinecraftOptionsStorage;
import net.minecraft.network.chat.TranslatableComponent;

@Mixin(SodiumGameOptionPages.class)
abstract class SodiumGameOptionPagesMixin {
	@Inject(method = "general()Lme/jellysquid/mods/sodium/client/gui/options/OptionPage;", at = @At("RETURN"),
			remap = false)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static final void redirectProcessGamma(final CallbackInfoReturnable<OptionPage> callback) {
		for (final OptionGroup group : callback.getReturnValue().getGroups()) {
			for (final Option<?> option : group.getOptions()) {
				if ((!(option.getStorage() instanceof MinecraftOptionsStorage) ||
						!(option.getName() instanceof final TranslatableComponent translatableName) ||
						!"options.gamma".equals(translatableName.getKey()) ||
						!(option instanceof final OptionImpl optionImpl))) {
					continue;
				}

				((OptionImplMixin) optionImpl).setBinding(new GenericBinding<>(
						(opts, gamma) -> BrightnessAutoTuneClientMod.getInstance().getConfig()
								.setBrightness(gamma * 0.01D),
						opts -> ((int) (BrightnessAutoTuneClientMod.getInstance().getConfig().getBrightness() /
								0.01D))));

				optionImpl.reset();
			}
		}
	}

	private SodiumGameOptionPagesMixin() {
	}
}
