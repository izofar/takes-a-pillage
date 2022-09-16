package com.izofar.takesapillage.client.renderer;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.client.model.SkirmisherModel;
import com.izofar.takesapillage.entity.SkirmisherEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkirmisherRenderer extends MobRenderer<SkirmisherEntity, SkirmisherModel> {

    private static final ResourceLocation SKIRMISHER = new ResourceLocation(TakesAPillageMod.MODID, "textures/entity/skirmisher.png");

    public SkirmisherRenderer(EntityRendererManager manager) {
        super(manager, new SkirmisherModel(), 0.5F);
        this.addLayer(new HeadLayer<>(this));
        this.addLayer(new HeldItemLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(SkirmisherEntity skirmisherEntity) {
        return SKIRMISHER;
    }

    @Override
    protected void scale(SkirmisherEntity skirmisherEntity, MatrixStack stack, float f) {
        stack.scale(0.9375F, 0.9375F, 0.9375F);
    }
}
