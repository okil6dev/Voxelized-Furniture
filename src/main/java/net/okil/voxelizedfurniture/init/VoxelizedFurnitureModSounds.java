/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.okil.voxelizedfurniture.init;

import net.okil.voxelizedfurniture.VoxelizedFurnitureMod;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

public class VoxelizedFurnitureModSounds {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, VoxelizedFurnitureMod.MODID);
	public static final DeferredHolder<SoundEvent, SoundEvent> DOORBELLRING = REGISTRY.register("doorbellring", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("voxelized_furniture", "doorbellring")));
}