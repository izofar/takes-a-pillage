package com.izofar.takesapillage.event;

import com.google.common.collect.ImmutableList;
import com.izofar.takesapillage.config.ModCommonConfigs;
import com.izofar.takesapillage.entity.ClayGolemEntity;
import com.izofar.takesapillage.init.ModEntityTypes;
import com.izofar.takesapillage.util.IMobRememberSpawnReason;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.LinkedList;
import java.util.Queue;

public abstract class ModEntityEvents {

    private static final Queue<ClayGolemEntity> CLAY_GOLEM_ENTITIES = new LinkedList<>();

    @SubscribeEvent
    public static void replaceNaturallySpawningIronGolemWithClayGolem(EntityJoinWorldEvent event) {
        if(!ModCommonConfigs.REPLACE_IRON_GOLEMS.get()) return;
        Entity entity = event.getEntity();
        if (entity instanceof IronGolemEntity
                && entity.getClass() == IronGolemEntity.class
                && event.getWorld() instanceof ServerWorld
                && ((IMobRememberSpawnReason)entity).getMobSpawnType() != SpawnReason.COMMAND
                && !((IronGolemEntity) entity).isPlayerCreated()) {
            ClayGolemEntity clayGolemEntity = ModEntityTypes.CLAY_GOLEM.get().create(event.getWorld());
            if(clayGolemEntity == null) return;
            clayGolemEntity.moveTo(entity.position());
            CLAY_GOLEM_ENTITIES.add(clayGolemEntity);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void checkForUnSpawnedGolem(TickEvent.WorldTickEvent event) {
        if(!ModCommonConfigs.REPLACE_IRON_GOLEMS.get()) return;
        World level = event.world;
        if (level instanceof ServerWorld) {
            Queue<ClayGolemEntity> clayGolemEntities = new LinkedList<>();
            for (ClayGolemEntity entity : CLAY_GOLEM_ENTITIES) {
                if (level.isAreaLoaded(entity.blockPosition(), 0)) {
                    level.addFreshEntity(entity);
                    clayGolemEntities.add(entity);
                }
            }
            CLAY_GOLEM_ENTITIES.removeAll(clayGolemEntities);
        }
    }

    @SubscribeEvent
    public static void preventMilkFromRemovingBadOmen(PotionEvent.PotionAddedEvent event) {
        if (event.getPotionEffect().getEffect().equals(Effects.BAD_OMEN)
                && !ModCommonConfigs.REMOVE_BAD_OMEN.get())
            event.getPotionEffect().setCurativeItems(ImmutableList.of());
    }
}
