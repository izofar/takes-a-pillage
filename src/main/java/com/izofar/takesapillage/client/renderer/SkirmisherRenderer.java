package com.izofar.takesapillage.client.renderer;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.client.model.SkirmisherModel;
import com.izofar.takesapillage.entity.Skirmisher;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class SkirmisherRenderer extends MobRenderer<Skirmisher, SkirmisherModel> {
    private static final ResourceLocation SKIRMISHER = new ResourceLocation(TakesAPillageMod.MOD_ID, "textures/entity/skirmisher.png");

    public SkirmisherRenderer(EntityRendererProvider.Context context) {
        super(context, new SkirmisherModel(SkirmisherModel.createBodyLayer().bakeRoot()), 0.5F);
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet()));
        this.addLayer(new ItemInHandLayer<>(this));
    }

    public ResourceLocation getTextureLocation(Skirmisher skirmisher) {
        return SKIRMISHER;
    }

    protected void scale(Skirmisher skirmisher, PoseStack stack, float f) {
        stack.scale(0.9375F, 0.9375F, 0.9375F);
    }
}
