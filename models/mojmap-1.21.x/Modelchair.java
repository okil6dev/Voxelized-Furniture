// Made with Blockbench 5.0.7
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

public class Modelchair<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "chair"), "main");
	private final ModelPart bb_main;

	public Modelchair(ModelPart root) {
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main",
				CubeListBuilder.create().texOffs(0, 0)
						.addBox(-6.0F, -8.0F, -6.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(4.0F, -8.0F, -6.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(4.0F, -8.0F, 4.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-6.0F, -8.0F, 4.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(3, 2)
						.addBox(-7.0F, -10.0F, 4.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(-2, 0)
						.addBox(-2.3F, -25.45F, 5.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(5.0F, -19.0F, 5.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(-7.0F, -19.0F, 5.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-2, 0)
						.addBox(-2.7F, -25.45F, 5.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(2, 1)
						.addBox(-4.0F, -10.0F, 4.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(4, 7)
						.addBox(-1.0F, -10.0F, 4.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(2, 1)
						.addBox(2.0F, -10.0F, 4.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(3, 6)
						.addBox(5.0F, -10.0F, 4.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(1, 7)
						.addBox(-7.0F, -10.0F, 1.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(3, 7)
						.addBox(-4.0F, -10.0F, 1.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(2, 10)
						.addBox(-1.0F, -10.0F, 1.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(3, 6)
						.addBox(2.0F, -10.0F, 1.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(3, 5)
						.addBox(5.0F, -10.0F, 1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(1, 7)
						.addBox(-7.0F, -10.0F, -2.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(2, 1)
						.addBox(-4.0F, -10.0F, -2.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(2, 1)
						.addBox(-1.0F, -10.0F, -2.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(4, 5)
						.addBox(2.0F, -10.0F, -2.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(3, 5)
						.addBox(5.0F, -10.0F, -2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(1, 7)
						.addBox(-7.0F, -10.0F, -5.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(3, 6)
						.addBox(-4.0F, -10.0F, -5.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(3, 6)
						.addBox(-1.0F, -10.0F, -5.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(3, 7)
						.addBox(2.0F, -10.0F, -5.0F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(3, 5)
						.addBox(5.0F, -10.0F, -5.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(5, 5)
						.addBox(-7.0F, -10.0F, -7.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(4, 8)
						.addBox(-4.0F, -10.0F, -7.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(4, 6)
						.addBox(-1.0F, -10.0F, -7.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(3, 2)
						.addBox(2.0F, -10.0F, -7.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(4, 8)
						.addBox(5.0F, -10.0F, -7.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(-5.0F, -12.0F, 5.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(-5.0F, -14.0F, 5.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(-5.0F, -14.0F, 5.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(-5.0F, -16.0F, 5.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(-5.0F, -18.0F, 5.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(-1, 0)
						.addBox(-5.0F, -20.0F, 5.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
						.addBox(-4.0F, -22.0F, 5.0F, 8.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(1, 0)
						.addBox(-3.0F, -24.0F, 5.0F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1",
				CubeListBuilder.create().texOffs(-1, 0).addBox(-2.0F, -3.6F, 0.0F, 2.0F, 4.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-3.8F, -21.5F, 5.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition cube_r2 = bb_main.addOrReplaceChild("cube_r2",
				CubeListBuilder.create().texOffs(-1, 0).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 4.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(6.25F, -21.9F, 5.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition cube_r3 = bb_main.addOrReplaceChild("cube_r3",
				CubeListBuilder.create().texOffs(-1, 0).addBox(-2.0F, -5.0F, 0.0F, 2.0F, 4.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(7.4F, -18.0F, 5.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition cube_r4 = bb_main.addOrReplaceChild("cube_r4",
				CubeListBuilder.create().texOffs(-1, 0).addBox(-2.0F, -3.6F, 0.0F, 2.0F, 4.0F, 2.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-5.0F, -18.6F, 5.0F, 0.0F, 0.0F, 0.3927F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
	}
}