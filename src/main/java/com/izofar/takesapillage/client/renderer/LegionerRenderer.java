package com.izofar.takesapillage.client.renderer;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.client.model.LegionerModel;
import com.izofar.takesapillage.entity.Legioner;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class LegionerRenderer extends IllagerRenderer<Legioner> {
    private static final ResourceLocation LEGIONER = new ResourceLocation(TakesAPillageMod.MOD_ID, "textures/entity/legioner.png");

    public LegionerRenderer(EntityRendererProvider.Context context) {
        super(context, new LegionerModel(context.bakeLayer(ModelLayers.PILLAGER)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this));
        this.model.getHat().visible = true;
    }

    public ResourceLocation getTextureLocation(Legioner archer) {
        return LEGIONER;
    }
}
