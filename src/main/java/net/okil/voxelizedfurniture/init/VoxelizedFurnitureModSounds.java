/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.okil.voxelizedfurniture.init;

import net.okil.voxelizedfurniture.VoxelizedFurnitureMod;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;

public class VoxelizedFurnitureModSounds {
	public static SoundEvent DOORBELLRING;

	public static void load() {
		DOORBELLRING = register("doorbellring", SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath("voxelized_furniture", "doorbellring")));
	}

	private static SoundEvent register(String registryname, SoundEvent element) {
		return Registry.register(BuiltInRegistries.SOUND_EVENT, Identifier.fromNamespaceAndPath(VoxelizedFurnitureMod.MODID, registryname), element);
	}
}