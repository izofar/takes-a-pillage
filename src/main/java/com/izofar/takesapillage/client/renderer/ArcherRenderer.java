package com.izofar.takesapillage.client.renderer;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.entity.Archer;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class ArcherRenderer extends IllagerRenderer<Archer> {
    private static final ResourceLocation ARCHER = new ResourceLocation(TakesAPillageMod.MOD_ID, "textures/entity/archer.png");

    public ArcherRenderer(EntityRendererProvider.Context context) {
        super(context, new IllagerModel<>(context.bakeLayer(ModelLayers.EVOKER)), 0.5F);
        this.addLayer(new ItemInHandLayer<>(this));
        this.model.getHat().visible = true;
    }

    public ResourceLocation getTextureLocation(Archer archer) {
        return ARCHER;
    }
}
