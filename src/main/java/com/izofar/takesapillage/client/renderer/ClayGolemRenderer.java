package com.izofar.takesapillage.client.renderer;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.client.model.ClayGolemModel;
import com.izofar.takesapillage.client.renderer.layer.ClayGolemCrackinessLayer;
import com.izofar.takesapillage.client.renderer.layer.ClayGolemFlowerLayer;
import com.izofar.takesapillage.entity.ClayGolem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClayGolemRenderer extends MobRenderer<ClayGolem, ClayGolemModel<ClayGolem>> {

    private static final ResourceLocation GOLEM_LOCATION = new ResourceLocation(TakesAPillageMod.MODID, "textures/entity/clay_golem/clay_golem.png");

    public ClayGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new ClayGolemModel(ClayGolemModel.createBodyLayer().bakeRoot()), 0.7F);
        this.addLayer(new ClayGolemCrackinessLayer(this));
        this.addLayer(new ClayGolemFlowerLayer(this));
    }

    public ResourceLocation getTextureLocation(ClayGolem entity) {
        return GOLEM_LOCATION;
    }

    protected void setupRotations(ClayGolem entity, PoseStack stack, float pitch, float yaw, float roll) {
        super.setupRotations(entity, stack, pitch, yaw, roll);
        if (entity.animationSpeed >= 0.01D) {
            float f1 = entity.animationPosition - entity.animationSpeed * (1.0F - roll) + 6.0F;
            float f2 = (Math.abs(f1 % 13.0F - 6.5F) - 3.25F) / 3.25F;
            stack.mulPose(Vector3f.ZP.rotationDegrees(6.5F * f2));
        }
    }
}
