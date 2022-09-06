package com.izofar.takesapillage.event;

import com.izofar.takesapillage.world.PillageSiege;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public abstract class ModWorldEvents {

    private static final PillageSiege siege = new PillageSiege();

    @SubscribeEvent
    public static void onSpecialSpawn(TickEvent.WorldTickEvent event) {
        Level level = event.world;
        if (level instanceof ServerLevel serverWorld) {
            siege.tick(serverWorld, true, false);
        }
    }
}
