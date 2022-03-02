package com.github.startsmercury.brightnessautotune.mixin.minecraft;

import static net.fabricmc.api.EnvType.CLIENT;

import java.util.List;
import java.util.ListIterator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.DebugScreenOverlay;

@Environment(CLIENT)
@Mixin(DebugScreenOverlay.class)
abstract class DebugScreenOverlayMixin {
	private DebugScreenOverlayMixin() {
	}

	@Inject(method = "getGameInformation()Ljava/util/List;", at = @At("RETURN"))
	@SuppressWarnings("resource")
	private final void addBrightnessInformation(final CallbackInfoReturnable<List<String>> callback) {
		final ListIterator<String> iterator = callback.getReturnValue().listIterator();

		while (iterator.hasNext()) {
			if (iterator.next().startsWith("Client Light: ")) {
				iterator.add("Brightness: " + (int) (Minecraft.getInstance().options.gamma * 100.0D) + '%');
			}
		}
	}
}
