package com.izofar.takesapillage.client;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.client.model.ClayGolemModel;
import com.izofar.takesapillage.client.model.LegionerModel;
import com.izofar.takesapillage.client.model.SkirmisherModel;
import com.izofar.takesapillage.client.renderer.ArcherRenderer;
import com.izofar.takesapillage.client.renderer.ClayGolemRenderer;
import com.izofar.takesapillage.client.renderer.LegionerRenderer;
import com.izofar.takesapillage.client.renderer.SkirmisherRenderer;
import com.izofar.takesapillage.init.ModEntityTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class TAPClient implements ClientModInitializer {
    public static final ModelLayerLocation CLAY_GOLEM = new ModelLayerLocation(new ResourceLocation(TakesAPillageMod.MOD_ID, "clay_golem"), "main");
    public static final ModelLayerLocation SKIRMISHER = new ModelLayerLocation(new ResourceLocation(TakesAPillageMod.MOD_ID, "skirmisher"), "main");
    public static final ModelLayerLocation LEGIONER = new ModelLayerLocation(new ResourceLocation(TakesAPillageMod.MOD_ID, "legioner"), "main");

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntityTypes.CLAY_GOLEM, ClayGolemRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.ARCHER, ArcherRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.SKIRMISHER, SkirmisherRenderer::new);
        EntityRendererRegistry.register(ModEntityTypes.LEGIONER, LegionerRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(CLAY_GOLEM, ClayGolemModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(SKIRMISHER, SkirmisherModel::createBodyLayer);
        EntityModelLayerRegistry.registerModelLayer(LEGIONER, LegionerModel::createBodyLayer);
    }
}
