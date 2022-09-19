package com.izofar.takesapillage.client.model;

import com.izofar.takesapillage.entity.LegionerEntity;
import net.minecraft.client.renderer.entity.model.IllagerModel;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LegionerModel extends IllagerModel<LegionerEntity> {

    public LegionerModel() {
        super(0, 0, 64, 64);
    }

    @Override
    public void setupAnim(LegionerEntity legionerEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(legionerEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        if(legionerEntity.isAlive() && legionerEntity.isUsingShield()){
            boolean flag = legionerEntity.getMainArm() == HandSide.RIGHT;
            if ((legionerEntity.getShieldHand() == Hand.MAIN_HAND) == flag) {
                this.poseRightArmShield();
            } else if ((legionerEntity.getShieldHand() == Hand.OFF_HAND) == flag){
                this.poseLeftArmShield();
            }
        }
    }
    
    private void poseRightArmShield(){
        this.rightArm.xRot = this.rightArm.xRot * 0.5F - 0.9424779F;
        this.rightArm.yRot = (-(float)Math.PI / 6F);
    }
    
    private void poseLeftArmShield(){
        this.leftArm.xRot = this.leftArm.xRot * 0.5F - 0.9424779F;
        this.leftArm.yRot = ((float)Math.PI / 6F);
    }

}
