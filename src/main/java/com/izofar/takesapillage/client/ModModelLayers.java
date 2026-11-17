package com.izofar.takesapillage.client;

import com.izofar.takesapillage.TakesAPillageMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModModelLayers {

    // Own layers instead of the vanilla pillager/evoker ones, so models made for vanilla illagers aren't applied to ours
    public static final ModelLayerLocation ARCHER = new ModelLayerLocation(new ResourceLocation(TakesAPillageMod.MODID, "archer"), "main");
    public static final ModelLayerLocation LEGIONER = new ModelLayerLocation(new ResourceLocation(TakesAPillageMod.MODID, "legioner"), "main");
}
