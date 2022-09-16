package com.izofar.takesapillage.client.renderer;


import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.client.model.LegionerModel;
import com.izofar.takesapillage.entity.LegionerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LegionerRenderer extends IllagerRenderer<LegionerEntity> {

    private static final ResourceLocation LEGIONER = new ResourceLocation(TakesAPillageMod.MODID, "textures/entity/legioner.png");

    public LegionerRenderer(EntityRendererManager manager) {
        super(manager, new LegionerModel(), 0.5F);
        this.addLayer(new HeldItemLayer<>(this));
        this.model.getHat().visible = true;
    }

    @Override
    public ResourceLocation getTextureLocation(LegionerEntity archer) {
        return LEGIONER;
    }
}
