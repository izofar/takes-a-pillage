package com.izofar.takesapillage.client.renderer;


import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.entity.Legioner;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LegionerRenderer extends IllagerRenderer<Legioner> {

    private static final ResourceLocation LEGIONER = new ResourceLocation(TakesAPillageMod.MODID, "textures/entity/legioner.png");

    public LegionerRenderer(EntityRendererProvider.Context context) {
        super(context, new IllagerModel(context.bakeLayer(ModelLayers.PILLAGER)), 0.5F);
        this.addLayer(new ItemInHandLayer(this));
        this.model.getHat().visible = true;
    }

    public ResourceLocation getTextureLocation(Legioner archer) {
        return LEGIONER;
    }
}
