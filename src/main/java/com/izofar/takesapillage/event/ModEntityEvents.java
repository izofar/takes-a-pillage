package com.izofar.takesapillage.event;

import com.google.common.collect.ImmutableList;
import com.izofar.takesapillage.client.renderer.ArcherRenderer;
import com.izofar.takesapillage.client.renderer.ClayGolemRenderer;
import com.izofar.takesapillage.client.renderer.LegionerRenderer;
import com.izofar.takesapillage.client.renderer.SkirmisherRenderer;
import com.izofar.takesapillage.config.ModCommonConfigs;
import com.izofar.takesapillage.entity.ArcherEntity;
import com.izofar.takesapillage.entity.ClayGolemEntity;
import com.izofar.takesapillage.entity.LegionerEntity;
import com.izofar.takesapillage.entity.SkirmisherEntity;
import com.izofar.takesapillage.init.ModEntityTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.LinkedList;
import java.util.Queue;

public abstract class ModEntityEvents {

    private static final Queue<ClayGolemEntity> golem_queue = new LinkedList<>();

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

    @SubscribeEvent
    public static void replaceNaturallySpawningIronGolemWithClayGolem(EntityJoinWorldEvent event) {
        if(!ModCommonConfigs.REPLACE_IRON_GOLEMS.get()) return;
        Entity entity = event.getEntity();
        if (entity instanceof IronGolemEntity
                && !(event.getEntity() instanceof ClayGolemEntity)
                && event.getWorld() instanceof ServerWorld
                && !((IronGolemEntity) entity).isPlayerCreated()) {
            ClayGolemEntity claygolementity = ModEntityTypes.CLAY_GOLEM.get().create(event.getWorld());
            if(claygolementity == null) return;
            claygolementity.moveTo(entity.position());
            golem_queue.add(claygolementity);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void checkForUnSpawnedGolem(TickEvent.WorldTickEvent event) {
        if(!ModCommonConfigs.REPLACE_IRON_GOLEMS.get()) return;
        World level = event.world;
        if (level instanceof ServerWorld) {
            Queue<ClayGolemEntity> remove_golem_queue = new LinkedList<>();
            for (ClayGolemEntity entity : golem_queue) {
                if (level.isAreaLoaded(entity.blockPosition(), 0)) {
                    level.addFreshEntity(entity);
                    remove_golem_queue.add(entity);
                }
            }
            golem_queue.removeAll(remove_golem_queue);
        }
    }

    @SubscribeEvent
    public static void preventMilkFromRemovingBadOmen(PotionEvent.PotionAddedEvent event) {
        if (event.getPotionEffect().getEffect().equals(Effects.BAD_OMEN)
                && !ModCommonConfigs.REMOVE_BAD_OMEN.get())
            event.getPotionEffect().setCurativeItems(ImmutableList.of());
    }
}
