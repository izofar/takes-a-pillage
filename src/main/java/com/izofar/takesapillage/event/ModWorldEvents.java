package com.izofar.takesapillage.event;

import com.izofar.takesapillage.config.ModCommonConfigs;
import com.izofar.takesapillage.init.ModEntityTypes;
import com.izofar.takesapillage.world.PillageSiege;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public abstract class ModWorldEvents {

    public static final PillageSiege PILLAGE_SIEGE = new PillageSiege();

    @SubscribeEvent
    public static void onSpecialSpawn(TickEvent.LevelTickEvent event) {
        if (event.level instanceof ServerLevel serverLevel
                && serverLevel.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)
                && ModCommonConfigs.PILLAGE_SIEGES_OCCUR.get()
                && serverLevel.dimension() == Level.OVERWORLD) {
            PILLAGE_SIEGE.tick(serverLevel, true, false);
        }
    }

    public static void addModdedRaiders() {
        Raid.RaiderType.create("ARCHER", ModEntityTypes.ARCHER.get(), new int[]{0, 2, 0, 0, 2, 3, 2, 4});
        Raid.RaiderType.create("SKIRMISHER", ModEntityTypes.SKIRMISHER.get(), new int[]{0, 0, 0, 2, 0, 1, 1, 2});
        Raid.RaiderType.create("LEGIONER", ModEntityTypes.LEGIONER.get(), new int[]{0, 0, 0, 0, 2, 0, 2, 3});
    }
}
