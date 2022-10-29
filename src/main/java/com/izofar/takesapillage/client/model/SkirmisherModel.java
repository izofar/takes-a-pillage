package com.izofar.takesapillage.client.model;

import com.izofar.takesapillage.entity.Skirmisher;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.AbstractIllager;

public class SkirmisherModel extends HierarchicalModel<Skirmisher> implements ArmedModel, HeadedModel {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart left_leg;
    private final ModelPart right_leg;

    public SkirmisherModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.left_arm = root.getChild("left_arm");
        this.right_arm = root.getChild("right_arm");
        this.left_leg = root.getChild("left_leg");
        this.right_leg = root.getChild("right_leg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition partdefinition1 = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), PartPose.ZERO);
        partdefinition1.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-0.5F, -15.0F, -5.0F, 1.0F, 6.0F, 10.0F, new CubeDeformation(-0.5F)), PartPose.ZERO);
        partdefinition1.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F), PartPose.offset(0.0F, -2.0F, 0.0F));
        partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F).texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-2.0F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(2.0F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), PartPose.offset(5.0F, 2.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public ModelPart root() {
        return root;
    }

    public void setupAnim(Skirmisher skirmisher, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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
            this.right_arm.xRot = Mth.cos(limbSwing * 0.6662F + 3.1415927F) * 2.0F * limbSwingAmount * 0.5F;
            this.right_arm.yRot = 0.0F;
            this.right_arm.zRot = 0.0F;
            this.left_arm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
            this.left_arm.yRot = 0.0F;
            this.left_arm.zRot = 0.0F;
            this.right_leg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
            this.right_leg.yRot = 0.0F;
            this.right_leg.zRot = 0.0F;
            this.left_leg.xRot = Mth.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount * 0.5F;
            this.left_leg.yRot = 0.0F;
            this.left_leg.zRot = 0.0F;
        }
        AbstractIllager.IllagerArmPose abstractillager$illagerarmpose = skirmisher.getArmPose();
        if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.ATTACKING) {
            if (!skirmisher.getMainHandItem().isEmpty())
                AnimationUtils.swingWeaponDown(this.right_arm, this.left_arm, skirmisher, this.attackTime, ageInTicks);
        } else if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.SPELLCASTING) {
            this.right_arm.z = 0.0F;
            this.right_arm.x = -5.0F;
            this.left_arm.z = 0.0F;
            this.left_arm.x = 5.0F;
            this.right_arm.xRot = Mth.cos(ageInTicks * 0.6662F) * 0.25F;
            this.left_arm.xRot = Mth.cos(ageInTicks * 0.6662F) * 0.25F;
            this.right_arm.zRot = 2.3561945F;
            this.left_arm.zRot = -2.3561945F;
            this.right_arm.yRot = 0.0F;
            this.left_arm.yRot = 0.0F;
        } else if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.BOW_AND_ARROW) {
            this.right_arm.yRot = -0.1F + this.head.yRot;
            this.right_arm.xRot = -1.5707964F + this.head.xRot;
            this.left_arm.xRot = -0.9424779F + this.head.xRot;
            this.head.yRot -= 0.4F;
            this.left_arm.zRot = 1.5707964F;
        } else if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.CROSSBOW_HOLD) {
            AnimationUtils.animateCrossbowHold(this.right_arm, this.left_arm, this.head, true);
        } else if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.CROSSBOW_CHARGE) {
            AnimationUtils.animateCrossbowCharge(this.right_arm, this.left_arm, skirmisher, true);
        } else if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.CELEBRATING) {
            this.right_arm.z = 0.0F;
            this.right_arm.x = -5.0F;
            this.right_arm.xRot = Mth.cos(ageInTicks * 0.6662F) * 0.05F;
            this.right_arm.zRot = 2.670354F;
            this.right_arm.yRot = 0.0F;
            this.left_arm.z = 0.0F;
            this.left_arm.x = 5.0F;
            this.left_arm.xRot = Mth.cos(ageInTicks * 0.6662F) * 0.05F;
            this.left_arm.zRot = -2.3561945F;
            this.left_arm.yRot = 0.0F;
        }
    }

    private ModelPart getArm(HumanoidArm arm) {
        return (arm == HumanoidArm.LEFT) ? this.left_arm : this.right_arm;
    }

    @Override
    public ModelPart getHead() {
        return this.head;
    }

    @Override
    public void translateToHand(HumanoidArm arm, PoseStack stack) {
        getArm(arm).translateAndRotate(stack);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.head.render(poseStack, buffer, packedLight, packedOverlay);
        this.body.render(poseStack, buffer, packedLight, packedOverlay);
        this.left_arm.render(poseStack, buffer, packedLight, packedOverlay);
        this.right_arm.render(poseStack, buffer, packedLight, packedOverlay);
        this.left_leg.render(poseStack, buffer, packedLight, packedOverlay);
        this.right_leg.render(poseStack, buffer, packedLight, packedOverlay);
    }
}
