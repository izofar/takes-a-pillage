package com.izofar.takesapillage.event;

import com.izofar.takesapillage.entity.ClayGolemEntity;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public abstract class ModBlockEvents {

    @SubscribeEvent
    public static void checkSpawnClayGolemOnBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (ClayGolemEntity.PUMPKINS_PREDICATE.test(event.getPlacedBlock())) {
            IWorld levelAccessor = event.getWorld();
            if (levelAccessor instanceof World) {
                ClayGolemEntity.trySpawnClayGolem((World) levelAccessor, event.getPos());
            }
        }
    }

}
