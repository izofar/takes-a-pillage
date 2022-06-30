package com.izofar.takesapillage;

import com.izofar.takesapillage.client.renderer.ArcherRenderer;
import com.izofar.takesapillage.client.renderer.ClayGolemRenderer;
import com.izofar.takesapillage.client.renderer.LegionerRenderer;
import com.izofar.takesapillage.client.renderer.SkirmisherRenderer;
import com.izofar.takesapillage.entity.Archer;
import com.izofar.takesapillage.entity.ClayGolem;
import com.izofar.takesapillage.entity.Legioner;
import com.izofar.takesapillage.entity.Skirmisher;
import com.izofar.takesapillage.entity.event.ModBlockEvents;
import com.izofar.takesapillage.entity.event.ModEntityEvents;
import com.izofar.takesapillage.entity.event.ModWorldEvents;
import com.izofar.takesapillage.init.ModEntityTypes;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public abstract class RegistryEvents {
    static {
        MinecraftForge.EVENT_BUS.register(ModBlockEvents.class);
        MinecraftForge.EVENT_BUS.register(ModEntityEvents.class);
        MinecraftForge.EVENT_BUS.register(ModWorldEvents.class);
    }

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.CLAY_GOLEM.get(), ClayGolem.createAttributes().build());
        event.put(ModEntityTypes.ARCHER.get(), Archer.createAttributes().build());
        event.put(ModEntityTypes.SKIRMISHER.get(), Skirmisher.createAttributes().build());
        event.put(ModEntityTypes.LEGIONER.get(), Legioner.createAttributes().build());
    }

    @SubscribeEvent
    public static void onRegistryRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.CLAY_GOLEM.get(), ClayGolemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.ARCHER.get(), ArcherRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SKIRMISHER.get(), SkirmisherRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.LEGIONER.get(), LegionerRenderer::new);
    }
}
