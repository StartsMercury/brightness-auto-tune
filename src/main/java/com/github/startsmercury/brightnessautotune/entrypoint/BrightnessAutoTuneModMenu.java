package com.github.startsmercury.brightnessautotune.entrypoint;

import com.github.startsmercury.brightnessautotune.modmenu.BrightnessAutoTuneClothConfigScreen;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import net.fabricmc.loader.api.FabricLoader;

public class BrightnessAutoTuneModMenu implements ModMenuApi {
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		if (FabricLoader.getInstance().isModLoaded("cloth-config")) {
			return BrightnessAutoTuneClothConfigScreen::create;
		} else {
			return ModMenuApi.super.getModConfigScreenFactory();
		}
	}
}
