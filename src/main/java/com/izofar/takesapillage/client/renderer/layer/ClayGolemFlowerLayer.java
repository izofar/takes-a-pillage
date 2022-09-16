package com.izofar.takesapillage.client.renderer.layer;

import com.izofar.takesapillage.client.model.ClayGolemModel;
import com.izofar.takesapillage.entity.ClayGolemEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClayGolemFlowerLayer extends LayerRenderer<ClayGolemEntity, ClayGolemModel> {

    public ClayGolemFlowerLayer(IEntityRenderer<ClayGolemEntity, ClayGolemModel> layer) {
        super(layer);
    }

    @Override
    public void render(MatrixStack stack, IRenderTypeBuffer buffer, int packedLight, ClayGolemEntity golem, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (golem.getOfferFlowerTick() == 0) return;
        stack.pushPose();
        ModelRenderer modelpart = this.getParentModel().getFlowerHoldingArm();
        modelpart.translateAndRotate(stack);
        stack.translate(-1.0185D, 0.78D, -0.9375D);
        stack.translate(0.5D, 0.5D, 0.5D);
        stack.scale(0.5F, 0.5F, 0.5F);
        stack.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
        stack.translate(-0.5D, -0.5D, -0.5D);
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(Blocks.POPPY.defaultBlockState(), stack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
        stack.popPose();
    }
}
