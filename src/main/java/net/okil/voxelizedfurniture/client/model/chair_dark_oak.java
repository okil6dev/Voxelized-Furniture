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
public class chair_dark_oak extends EntityModel<LivingEntityRenderState> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("voxelized_furniture", "chair_dark_oak"), "main");
	public final ModelPart bb_main;

	public chair_dark_oak(ModelPart root) {
		super(root);
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main",
				CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -8.0F, -6.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(4.0F, -8.0F, -6.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(4.0F, -8.0F, 4.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-6.0F, -8.0F, 4.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(3, 2)
						.addBox(-7.0F, -10.0F, 4.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(-2, 0).addBox(-2.3F, -25.45F, 5.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(5.0F, -19.0F, 5.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0).addBox(-7.0F, -19.0F, 5.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-2, 0)
						.addBox(-2.7F, -25.45F, 5.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(2, 1).addBox(-4.0F, -10.0F, 4.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(4, 7)
						.addBox(-1.0F, -10.0F, 4.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(2, 1).addBox(2.0F, -10.0F, 4.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(3, 6)
						.addBox(5.0F, -10.0F, 4.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(1, 7).addBox(-7.0F, -10.0F, 1.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(3, 7)
						.addBox(-4.0F, -10.0F, 1.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(2, 10).addBox(-1.0F, -10.0F, 1.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(3, 6)
						.addBox(2.0F, -10.0F, 1.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(3, 5).addBox(5.0F, -10.0F, 1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(1, 7)
						.addBox(-7.0F, -10.0F, -2.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(2, 1).addBox(-4.0F, -10.0F, -2.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(2, 1)
						.addBox(-1.0F, -10.0F, -2.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(4, 5).addBox(2.0F, -10.0F, -2.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(3, 5)
						.addBox(5.0F, -10.0F, -2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(1, 7).addBox(-7.0F, -10.0F, -5.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(3, 6)
						.addBox(-4.0F, -10.0F, -5.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(3, 6).addBox(-1.0F, -10.0F, -5.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(3, 7)
						.addBox(2.0F, -10.0F, -5.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(3, 5).addBox(5.0F, -10.0F, -5.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(5, 5)
						.addBox(-7.0F, -10.0F, -7.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(4, 8).addBox(-4.0F, -10.0F, -7.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(4, 6)
						.addBox(-1.0F, -10.0F, -7.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(3, 2).addBox(2.0F, -10.0F, -7.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(4, 8)
						.addBox(5.0F, -10.0F, -7.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0).addBox(-5.0F, -12.0F, 5.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(-5.0F, -14.0F, 5.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0).addBox(-5.0F, -14.0F, 5.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(-5.0F, -16.0F, 5.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0).addBox(-5.0F, -18.0F, 5.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(-5.0F, -20.0F, 5.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-4.0F, -22.0F, 5.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(1, 0)
						.addBox(-3.0F, -24.0F, 5.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(-1, 0).addBox(-2.0F, -3.6F, 0.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-3.8F, -21.5F, 5.0F, 0.0F, 0.0F, 0.7854F));
		PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(-1, 0).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(6.25F, -21.9F, 5.0F, 0.0F, 0.0F, -0.7854F));
		PartDefinition cube_r3 = bb_main.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(-1, 0).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(7.4F, -18.0F, 5.0F, 0.0F, 0.0F, -0.3927F));
		PartDefinition cube_r4 = bb_main.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(-1, 0).addBox(-2.0F, -3.6F, 0.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-5.0F, -18.6F, 5.0F, 0.0F, 0.0F, 0.3927F));
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