package com.izofar.takesapillage.client.renderer;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.client.model.ClayGolemModel;
import com.izofar.takesapillage.client.renderer.layer.ClayGolemCrackinessLayer;
import com.izofar.takesapillage.client.renderer.layer.ClayGolemFlowerLayer;
import com.izofar.takesapillage.entity.ClayGolemEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClayGolemRenderer extends MobRenderer<ClayGolemEntity, ClayGolemModel> {

    private static final ResourceLocation GOLEM_LOCATION = new ResourceLocation(TakesAPillageMod.MODID, "textures/entity/clay_golem/clay_golem.png");

    public ClayGolemRenderer(EntityRendererManager manager) {
        super(manager, new ClayGolemModel(), 0.7F);
        this.addLayer(new ClayGolemCrackinessLayer(this));
        this.addLayer(new ClayGolemFlowerLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(ClayGolemEntity entity) {
        return GOLEM_LOCATION;
    }

    @Override
    protected void setupRotations(ClayGolemEntity entity, MatrixStack stack, float pitch, float yaw, float roll) {
        super.setupRotations(entity, stack, pitch, yaw, roll);
        if (entity.animationSpeed >= 0.01D) {
            float f1 = entity.animationPosition - entity.animationSpeed * (1.0F - roll) + 6.0F;
            float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
            stack.mulPose(Vector3f.ZP.rotationDegrees(6.5F * f2));
        }
    }
}
