package net.okil.voxelizedfurniture.client.model;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.EntityModel;

// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class SlidingDoor extends EntityModel<LivingEntityRenderState> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("voxelized_furniture", "sliding_door"), "main");
	public final ModelPart bone;

	public SlidingDoor(ModelPart root) {
		super(root);
		this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition bone = partdefinition.addOrReplaceChild("bone",
				CubeListBuilder.create().texOffs(0, 0).addBox(-1.5172F, 3.0345F, -1.0F, 6.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-7.5172F, 3.0345F, -1.0F, 6.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(2, 0)
						.addBox(4.4828F, 3.0345F, -1.0F, 4.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-7.5172F, -10.9655F, -1.0F, 6.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(2, 0)
						.addBox(4.4828F, -10.9655F, -1.0F, 4.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-1.5172F, -10.9655F, -1.0F, 6.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(2, 0)
						.addBox(4.4828F, -14.9655F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-1.5172F, -14.9655F, -1.0F, 6.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-7.5172F, -14.9655F, -1.0F, 6.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 2).addBox(1.4828F, -14.9655F, -1.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(5, 0)
						.addBox(7.4828F, 1.0345F, -1.5F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(5, 0).addBox(7.4828F, -13.9655F, -1.5F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(5, 0)
						.addBox(-7.5172F, 1.0345F, -1.5F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(5, 0).addBox(-7.5172F, -13.9655F, -1.5F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 2)
						.addBox(-5.5172F, -14.9655F, -1.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 2).addBox(-7.5172F, -14.9655F, -1.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 2)
						.addBox(-7.5172F, 16.0345F, -1.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 2).addBox(-5.5172F, 16.0345F, -1.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 2)
						.addBox(1.4828F, 16.0345F, -1.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 2).addBox(-7.5172F, -14.9655F, 0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 2)
						.addBox(-5.5172F, -14.9655F, 0.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 2).addBox(1.4828F, -14.9655F, 0.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(5, 0)
						.addBox(7.4828F, 1.0345F, 0.5F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(5, 0).addBox(7.4828F, -13.9655F, 0.5F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(5, 0)
						.addBox(-7.5172F, -13.9655F, 0.5F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(5, 0).addBox(-7.5172F, 1.0345F, 0.5F, 1.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 2)
						.addBox(-7.5172F, 16.0345F, 0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 2).addBox(-5.5172F, 16.0345F, 0.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 2)
						.addBox(1.4828F, 16.0345F, 0.5F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-0.4828F, 6.9655F, 0.0F));
		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	public void setupAnim(LivingEntityRenderState state) {
		float limbSwing = state.walkAnimationPos;
		float limbSwingAmount = state.walkAnimationSpeed;
		float ageInTicks = state.ageInTicks;
		float netHeadYaw = state.yRot;
		float headPitch = state.xRot;

	}

}