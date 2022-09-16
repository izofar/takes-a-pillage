package com.izofar.takesapillage.client.renderer;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.entity.ArcherEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.model.IllagerModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ArcherRenderer extends IllagerRenderer<ArcherEntity> {

    private static final ResourceLocation ARCHER = new ResourceLocation(TakesAPillageMod.MODID, "textures/entity/archer.png");

    public ArcherRenderer(EntityRendererManager manager) {
        super(manager, new IllagerModel<>(0, 0, 64, 64), 0.5F);
        this.addLayer(new HeldItemLayer<>(this));
        this.model.getHat().visible = true;
    }

    @Override
    public ResourceLocation getTextureLocation(ArcherEntity archerEntity) {
        return ARCHER;
    }
}
