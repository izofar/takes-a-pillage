package com.izofar.takesapillage.client.model;

import com.google.common.collect.ImmutableList;
import com.izofar.takesapillage.entity.ClayGolemEntity;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class ClayGolemModel extends SegmentedModel<ClayGolemEntity> {

    private final ModelRenderer head;
    private final ModelRenderer body;
    private final ModelRenderer right_arm;
    private final ModelRenderer left_arm;
    private final ModelRenderer right_leg;
    private final ModelRenderer left_leg;

    public ClayGolemModel() {
        this.texWidth = 128;
        this.texHeight = 128;
        this.head = new ModelRenderer(this);
        this.body = new ModelRenderer(this);
        this.right_arm = new ModelRenderer(this);
        this.left_arm = new ModelRenderer(this);
        this.right_leg = new ModelRenderer(this);
        this.left_leg = new ModelRenderer(this);

        this.head.texOffs(36, 21).addBox(-4.0F, -11.0F, -5.5F, 8.0F, 9.0F, 8.0F)
                .texOffs(0, 0).addBox(-1.0F, -5.0F, -7.5F, 2.0F, 3.0F, 2.0F)
                .setPos(0.0F, -7.0F, 0.0F);
        this.body.texOffs(0, 0).addBox(-7.0F, -2.0F, -5.0F, 14.0F, 12.0F, 9.0F)
                .texOffs(36, 38).addBox(-4.5F, 10.0F, -3.0F, 9.0F, 5.0F, 6.0F, 0.5F);
        this.body.setPos(0.0F, -7.0F, 0.0F);
        this.right_arm.texOffs(18, 21).addBox(-10.0F, -2.5F, -3.0F, 3.0F, 25.0F, 6.0F)
                .setPos(0.0F, -7.0F, 0.0F);
        this.left_arm.texOffs(0, 21).addBox(7.0F, -2.5F, -3.0F, 3.0F, 25.0F, 6.0F)
                .setPos(0.0F, -7.0F, 0.0F);
        this.right_leg.texOffs(31, 49).addBox(-9.5F, -3.0F, -3.0F, 4.0F, 16.0F, 5.0F)
                .setPos(4.0F, 11.0F, 0.0F);
        this.left_leg.texOffs(46, 0).addBox(6.5F, -3.0F, -3.0F, 4.0F, 16.0F, 5.0F)
                .setPos(-5.0F, 11.0F, 0.0F);
    }

    @Override
    public Iterable<ModelRenderer> parts() {
        return ImmutableList.of(this.head, this.body, this.right_leg, this.left_leg, this.right_arm, this.left_arm);
    }

    public void setupAnim(ClayGolemEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float newHeadYaw, float headPitch) {
        this.head.yRot = newHeadYaw * 0.017453292F;
        this.head.xRot = headPitch * 0.017453292F;
        this.right_leg.xRot = -1.5F * MathHelper.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
        this.left_leg.xRot = 1.5F * MathHelper.triangleWave(limbSwing, 13.0F) * limbSwingAmount;
        this.right_leg.yRot = 0.0F;
        this.left_leg.yRot = 0.0F;
    }

    public void prepareMobModel(ClayGolemEntity entity, float t, float theta, float phi) {
        int i = entity.getAttackAnimationTick();
        if (i > 0) {
            this.right_arm.xRot = -2.0F + 1.5F * MathHelper.triangleWave(i - phi, 10.0F);
            this.left_arm.xRot = -2.0F + 1.5F * MathHelper.triangleWave(i - phi, 10.0F);
        } else {
            int j = entity.getOfferFlowerTick();
            if (j > 0) {
                this.right_arm.xRot = -0.8F + 0.025F * MathHelper.triangleWave(j, 70.0F);
                this.left_arm.xRot = 0.0F;
            } else {
                this.right_arm.xRot = (-0.2F + 1.5F * MathHelper.triangleWave(t, 13.0F)) * theta;
                this.left_arm.xRot = (-0.2F - 1.5F * MathHelper.triangleWave(t, 13.0F)) * theta;
            }
        }
    }

    public ModelRenderer getFlowerHoldingArm() {
        return this.right_arm;
    }
}
