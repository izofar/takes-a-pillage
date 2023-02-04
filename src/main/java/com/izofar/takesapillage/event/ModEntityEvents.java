package com.izofar.takesapillage.event;

import com.google.common.collect.ImmutableList;
import com.izofar.takesapillage.config.ModCommonConfigs;
import com.izofar.takesapillage.entity.ClayGolem;
import com.izofar.takesapillage.init.ModEntityTypes;
import com.izofar.takesapillage.util.IMobRememberSpawnReason;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.LinkedList;
import java.util.Queue;

public abstract class ModEntityEvents {

    private static final Queue<ClayGolem> CLAY_GOLEMS = new LinkedList<>();

    @SubscribeEvent
    public static void replaceNaturallySpawningIronGolemWithClayGolem(EntityJoinLevelEvent event) {
        if(!ModCommonConfigs.REPLACE_IRON_GOLEMS.get()) return;
        double replacementRate = ModCommonConfigs.getReplacementRate(event.getLevel().getDifficulty());
        Entity entity = event.getEntity();
        if (entity instanceof IronGolem ironGolem
                && entity.getClass() == IronGolem.class
                && event.getLevel() instanceof ServerLevel serverLevel
                && ((IMobRememberSpawnReason)ironGolem).getMobSpawnType() != MobSpawnType.COMMAND
                && !ironGolem.isPlayerCreated()
                && event.getLevel().getRandom().nextFloat() < replacementRate) {
            ClayGolem clayGolem = ModEntityTypes.CLAY_GOLEM.get().create(serverLevel);
            if(clayGolem == null) return;
            clayGolem.moveTo(ironGolem.position());
            CLAY_GOLEMS.add(clayGolem);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void checkForUnSpawnedGolem(TickEvent.LevelTickEvent event) {
        if(!ModCommonConfigs.REPLACE_IRON_GOLEMS.get()) return;
        if (event.level instanceof ServerLevel serverLevel) {
            Queue<ClayGolem> clayGolemQueue = new LinkedList<>();
            for (ClayGolem entity : CLAY_GOLEMS) {
                if (serverLevel.isAreaLoaded(entity.blockPosition(), 0)) {
                    serverLevel.addFreshEntity(entity);
                    clayGolemQueue.add(entity);
                }
            }
            CLAY_GOLEMS.removeAll(clayGolemQueue);
        }
    }

    @SubscribeEvent
    public static void preventMilkFromRemovingBadOmen(MobEffectEvent event) {
        MobEffectInstance effectInstance;
        if ((effectInstance = event.getEffectInstance()) != null
                && effectInstance.getEffect().equals(MobEffects.BAD_OMEN)
                && !ModCommonConfigs.REMOVE_BAD_OMEN.get()) {
            event.getEffectInstance().setCurativeItems(ImmutableList.of());
        }
    }
}
