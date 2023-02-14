package com.izofar.takesapillage.event;

import com.izofar.takesapillage.config.ModCommonConfigs;
import com.izofar.takesapillage.entity.ClayGolem;
import com.izofar.takesapillage.init.ModEntityTypes;
import com.izofar.takesapillage.util.IMobRememberSpawnReason;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.IronGolem;

import java.util.LinkedList;
import java.util.Queue;

public class ModEntityEvents {
    private static final Queue<ClayGolem> CLAY_GOLEMS = new LinkedList<>();

    public static void replaceNaturallySpawningIronGolemsWithClayGolems() {
        ServerEntityEvents.ENTITY_LOAD.register((entity, level) -> {
            if(!ModCommonConfigs.REPLACE_IRON_GOLEMS) return;
            if(entity instanceof IronGolem ironGolem && entity.getClass() == IronGolem.class && ((IMobRememberSpawnReason)ironGolem).getMobSpawnType() != MobSpawnType.COMMAND && !ironGolem.isPlayerCreated()) {
                ClayGolem clayGolem = ModEntityTypes.CLAY_GOLEM.create(level);
                if(clayGolem == null) return;
                clayGolem.moveTo(ironGolem.position());
                CLAY_GOLEMS.add(clayGolem);
                ironGolem.discard();
            }
        });
    }

    public static void checkForUnSpawnedGolem() {
        ServerTickEvents.END_WORLD_TICK.register(level -> {
            if(!ModCommonConfigs.REPLACE_IRON_GOLEMS) return;
            Queue<ClayGolem> clayGolemQueue = new LinkedList<>();
            for(ClayGolem entity : CLAY_GOLEMS) {
                level.addFreshEntity(entity);
                clayGolemQueue.add(entity);
            }
            CLAY_GOLEMS.removeAll(clayGolemQueue);
        });
    }
}
