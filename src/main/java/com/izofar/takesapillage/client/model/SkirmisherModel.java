package com.izofar.takesapillage.client.model;

import com.google.common.collect.ImmutableList;
import com.izofar.takesapillage.entity.SkirmisherEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.model.IHasArm;
import net.minecraft.client.renderer.entity.model.IHasHead;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelHelper;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;

public class SkirmisherModel extends SegmentedModel<SkirmisherEntity> implements IHasArm, IHasHead {

    private final ModelRenderer head;
    private final ModelRenderer hat;
    private final ModelRenderer nose;
    private final ModelRenderer body;
    private final ModelRenderer left_arm;
    private final ModelRenderer right_arm;
    private final ModelRenderer left_leg;
    private final ModelRenderer right_leg;

    public SkirmisherModel() {
        this.head = new ModelRenderer(this);
        this.hat = new ModelRenderer(this);
        this.nose = new ModelRenderer(this);
        this.head.addChild(hat);
        this.head.addChild(nose);
        this.body = new ModelRenderer(this);
        this.left_arm = new ModelRenderer(this);
        this.right_arm =new ModelRenderer(this);
        this.left_leg =new ModelRenderer(this);
        this.right_leg = new ModelRenderer(this);

        this.head.texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F).setPos(0, 0, 0);
        this.hat.texOffs(32, 0).addBox(-0.5F, -15.0F, -5.0F, 1.0F, 6.0F, 10.0F, -0.5F);
        this.hat.setPos(0, 0, 0);
        this.nose.texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F).setPos(0.0F, -2.0F, 0.0F);
        this.body.texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F).texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, 0.5F);
        this.body.setPos(0, 0, 0);
        this.right_leg.texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F).setPos(-2.0F, 12.0F, 0.0F);
        this.left_leg.texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F).setPos(2.0F, 12.0F, 0.0F);
        this.left_leg.mirror = true;
        this.right_arm.texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F).setPos(-5.0F, 2.0F, 0.0F);
        this.left_arm.texOffs(40, 46).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F).setPos(5.0F, 2.0F, 0.0F);
        this.left_arm.mirror = true;
    }

    public Iterable<ModelRenderer> parts() {
        return ImmutableList.of(this.head, this.body, this.left_leg, this.right_leg, this.right_arm, this.left_arm);
    }

    public void setupAnim(SkirmisherEntity skirmisherEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * 0.017453292F;
        this.head.xRot = headPitch * 0.017453292F;
        if (this.riding) {
            this.right_arm.xRot = -0.62831855F;
            this.right_arm.yRot = 0.0F;
            this.right_arm.zRot = 0.0F;
            this.left_arm.xRot = -0.62831855F;
            this.left_arm.yRot = 0.0F;
            this.left_arm.zRot = 0.0F;
            this.right_leg.xRot = -1.4137167F;
            this.right_leg.yRot = 0.31415927F;
            this.right_leg.zRot = 0.07853982F;
            this.left_leg.xRot = -1.4137167F;
            this.left_leg.yRot = -0.31415927F;
            this.left_leg.zRot = -0.07853982F;
        } else {
            this.right_arm.xRot = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 2.0F * limbSwingAmount * 0.5F;
            this.right_arm.yRot = 0.0F;
            this.right_arm.zRot = 0.0F;
            this.left_arm.xRot = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
            this.left_arm.yRot = 0.0F;
            this.left_arm.zRot = 0.0F;
            this.right_leg.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
            this.right_leg.yRot = 0.0F;
            this.right_leg.zRot = 0.0F;
            this.left_leg.xRot = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount * 0.5F;
            this.left_leg.yRot = 0.0F;
            this.left_leg.zRot = 0.0F;
        }
        AbstractIllagerEntity.ArmPose abstractillager$illagerarmpose = skirmisherEntity.getArmPose();
        if (abstractillager$illagerarmpose == AbstractIllagerEntity.ArmPose.ATTACKING) {
            if (!skirmisherEntity.getMainHandItem().isEmpty())
                ModelHelper.swingWeaponDown(this.right_arm, this.left_arm, skirmisherEntity, this.attackTime, ageInTicks);
        } else if (abstractillager$illagerarmpose == AbstractIllagerEntity.ArmPose.SPELLCASTING) {
            this.right_arm.z = 0.0F;
            this.right_arm.x = -5.0F;
            this.left_arm.z = 0.0F;
            this.left_arm.x = 5.0F;
            this.right_arm.xRot = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
            this.left_arm.xRot = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
            this.right_arm.zRot = 2.3561945F;
            this.left_arm.zRot = -2.3561945F;
            this.right_arm.yRot = 0.0F;
            this.left_arm.yRot = 0.0F;
        } else if (abstractillager$illagerarmpose == AbstractIllagerEntity.ArmPose.BOW_AND_ARROW) {
            this.right_arm.yRot = -0.1F + this.head.yRot;
            this.right_arm.xRot = -1.5707964F + this.head.xRot;
            this.left_arm.xRot = -0.9424779F + this.head.xRot;
            this.head.yRot -= 0.4F;
            this.left_arm.zRot = 1.5707964F;
        } else if (abstractillager$illagerarmpose == AbstractIllagerEntity.ArmPose.CROSSBOW_HOLD) {
            ModelHelper.animateCrossbowHold(this.right_arm, this.left_arm, this.head, true);
        } else if (abstractillager$illagerarmpose == AbstractIllagerEntity.ArmPose.CROSSBOW_CHARGE) {
            ModelHelper.animateCrossbowCharge(this.right_arm, this.left_arm, skirmisherEntity, true);
        } else if (abstractillager$illagerarmpose == AbstractIllagerEntity.ArmPose.CELEBRATING) {
            this.right_arm.z = 0.0F;
            this.right_arm.x = -5.0F;
            this.right_arm.xRot = MathHelper.cos(ageInTicks * 0.6662F) * 0.05F;
            this.right_arm.zRot = 2.670354F;
            this.right_arm.yRot = 0.0F;
            this.left_arm.z = 0.0F;
            this.left_arm.x = 5.0F;
            this.left_arm.xRot = MathHelper.cos(ageInTicks * 0.6662F) * 0.05F;
            this.left_arm.zRot = -2.3561945F;
            this.left_arm.yRot = 0.0F;
        }
    }

    private ModelRenderer getArm(HandSide arm) {
        return (arm == HandSide.LEFT) ? this.left_arm : this.right_arm;
    }

    @Override
    public ModelRenderer getHead() {
        return this.head;
    }

    @Override
    public void translateToHand(HandSide arm, MatrixStack stack) {
        this.getArm(arm).translateAndRotate(stack);
    }

}
