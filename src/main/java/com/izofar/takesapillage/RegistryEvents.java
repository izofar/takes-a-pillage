package com.izofar.takesapillage;

import com.izofar.takesapillage.client.renderer.ArcherRenderer;
import com.izofar.takesapillage.client.renderer.ClayGolemRenderer;
import com.izofar.takesapillage.client.renderer.LegionerRenderer;
import com.izofar.takesapillage.client.renderer.SkirmisherRenderer;
import com.izofar.takesapillage.entity.ArcherEntity;
import com.izofar.takesapillage.entity.ClayGolemEntity;
import com.izofar.takesapillage.entity.LegionerEntity;
import com.izofar.takesapillage.entity.SkirmisherEntity;
import com.izofar.takesapillage.event.ModBlockEvents;
import com.izofar.takesapillage.event.ModEntityEvents;
import com.izofar.takesapillage.event.ModWorldEvents;
import com.izofar.takesapillage.init.ModEntityTypes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public abstract class RegistryEvents {
    static {
        MinecraftForge.EVENT_BUS.register(ModBlockEvents.class);
        MinecraftForge.EVENT_BUS.register(ModEntityEvents.class);
        MinecraftForge.EVENT_BUS.register(ModWorldEvents.class);
    }

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.CLAY_GOLEM.get(), ClayGolemEntity.createAttributes().build());
        event.put(ModEntityTypes.ARCHER.get(), ArcherEntity.createAttributes().build());
        event.put(ModEntityTypes.SKIRMISHER.get(), SkirmisherEntity.createAttributes().build());
        event.put(ModEntityTypes.LEGIONER.get(), LegionerEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void onRegistryRenderers(final FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.CLAY_GOLEM.get(), ClayGolemRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.ARCHER.get(), ArcherRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.SKIRMISHER.get(), SkirmisherRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.LEGIONER.get(), LegionerRenderer::new);
    }
}
