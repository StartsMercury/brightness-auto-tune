package com.github.startsmercury.brightnessautotune.mixin;

import java.util.List;
import java.util.Set;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import net.fabricmc.loader.api.FabricLoader;

public class ModMixinPlugin implements IMixinConfigPlugin {
	private final boolean shouldApply;

	public ModMixinPlugin(final String modid) {
		this.shouldApply = FabricLoader.getInstance().isModLoaded(modid);
	}

	@Override
	public void acceptTargets(final Set<String> myTargets, final Set<String> otherTargets) {
	}

	@Override
	public List<String> getMixins() {
		return null;
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public void onLoad(final String mixinPackage) {
	}

	@Override
	public void postApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName,
			final IMixinInfo mixinInfo) {
	}

	@Override
	public void preApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName,
			final IMixinInfo mixinInfo) {
	}

	@Override
	public boolean shouldApplyMixin(final String targetClassName, final String mixinClassName) {
		return this.shouldApply;
	}
}
