package com.izofar.takesapillage.event;

import com.izofar.takesapillage.world.PillageSiege;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

public class ModWorldEvents {
    public static final PillageSiege PILLAGE_SIEGE = new PillageSiege();

    public static void onSpecialSpawn() {
        ServerTickEvents.START_WORLD_TICK.register(level -> {
            if(level != null && level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING) && level.dimension() == Level.OVERWORLD) {
                PILLAGE_SIEGE.tick(level, true, false);
            }
        });
    }
}
