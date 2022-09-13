package com.izofar.takesapillage.event;

import com.izofar.takesapillage.entity.ClayGolem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public abstract class ModBlockEvents {

    @SubscribeEvent
    public static void checkSpawnClayGolemOnBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (ClayGolem.PUMPKINS_PREDICATE.test(event.getPlacedBlock())) {
            LevelAccessor levelAccessor = event.getLevel();
            if (levelAccessor instanceof Level world) {
                ClayGolem.trySpawnClayGolem(world, event.getPos());
            }
        }
    }

}
