package net.okil.voxelizedfurniture.client.model;

import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.EntityModel;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports
public class ModelPottedBambooPlant<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("voxelized_furniture", "model_potted_bamboo_plant"), "main");
	public final ModelPart bone;

	public ModelPottedBambooPlant(ModelPart root) {
		this.bone = root.getChild("bone");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition bone = partdefinition.addOrReplaceChild("bone",
				CubeListBuilder.create().texOffs(14, 9).addBox(-6.5F, -1.0F, 5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(14, 9).addBox(-10.5F, -1.0F, 5.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(14, 9)
						.addBox(-10.5F, -1.0F, 9.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(14, 0).addBox(-11.0F, -2.0F, 5.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(14, 0)
						.addBox(-11.0F, -2.0F, 8.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(14, 4).addBox(-8.0F, -2.0F, 8.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(14, 4)
						.addBox(-8.0F, -2.0F, 5.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(24, 24).addBox(-8.0F, -8.0F, 5.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(26, 0)
						.addBox(-10.0F, -8.0F, 5.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(24, 24).addBox(-8.0F, -8.0F, 10.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(26, 0)
						.addBox(-10.0F, -8.0F, 10.0F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 0).addBox(-11.0F, -8.0F, 5.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-6.0F, -8.0F, 5.0F, 1.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(22, 17).addBox(-8.0F, -7.0F, 6.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 23)
						.addBox(-8.0F, -7.0F, 8.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(16, 24).addBox(-10.0F, -7.0F, 8.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 30)
						.addBox(-8.0F, -15.0F, 7.5F, 0.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(2, 30).addBox(-8.5F, -15.0F, 8.0F, 1.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(4, 30)
						.addBox(-7.9F, -20.0F, 7.6F, 0.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(26, 12).addBox(-8.4F, -20.0F, 8.1F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(26, 7)
						.addBox(-8.0F, -16.0F, 6.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(12, 12).addBox(-11.5F, -21.0F, 8.0F, 7.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(14, 8)
						.addBox(-10.5F, -22.0F, 8.0F, 5.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(14, 8).addBox(-10.5F, -16.0F, 8.0F, 5.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(13, 18)
						.addBox(-8.0F, -22.0F, 6.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 12).addBox(-8.0F, -21.0F, 5.0F, 0.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(8, 23)
						.addBox(-10.0F, -7.0F, 6.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(14, 9).addBox(-6.5F, -1.0F, 9.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(8.0F, 24.0F, -8.0F));
		PartDefinition cube_r1 = bone.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(14, 8).addBox(-2.5F, 2.5F, 0.0F, 5.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(14, 8)
				.addBox(-2.5F, -3.5F, 0.0F, 5.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(12, 12).addBox(-3.5F, -2.5F, 0.0F, 7.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -18.5F, 8.0F, 0.0F, 0.7854F, 0.0F));
		PartDefinition cube_r2 = bone.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(14, 8).addBox(-2.5F, -3.5F, 0.0F, 5.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(14, 8)
				.addBox(-2.5F, 2.5F, 0.0F, 5.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(12, 12).addBox(-3.5F, -2.5F, 0.0F, 7.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -18.5F, 8.0F, 0.0F, -0.7854F, 0.0F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
}