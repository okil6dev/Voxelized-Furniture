package net.okil.voxelizedfurniture.world.features;

import net.minecraft.world.level.levelgen.feature.configurations.CompositeFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.SimpleRandomSelectorFeature;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.Registries;

import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;

import java.util.function.Predicate;

public class BambooclusterfeatureFeature extends SimpleRandomSelectorFeature {
	public BambooclusterfeatureFeature() {
		super(CompositeFeatureConfiguration.CODEC);
	}

	public static final Predicate<BiomeSelectionContext> GENERATE_BIOMES = BiomeSelectors.tag(TagKey.create(Registries.BIOME, Identifier.parse("minecraft:is_overworld")));
}