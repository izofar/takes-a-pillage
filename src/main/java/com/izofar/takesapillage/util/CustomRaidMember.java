package com.izofar.takesapillage.util;

import net.minecraft.world.entity.raid.Raid;

public class CustomRaidMember {
    static {
        Raid.RaiderType.values();
    }

    public static final String ARCHER_INTERNAL_NAME = "ARCHER";
    public static final int[] ARCHER_COUNT_IN_WAVES = {0, 2, 0, 0, 2, 3, 2, 4};
    public static Raid.RaiderType ARCHER;

    public static final String SKIRMISHER_INTERNAL_NAME = "SKIRMISHER";
    public static final int[] SKIRMISHER_COUNT_IN_WAVES = {0, 0, 0, 2, 0, 1, 1, 2};
    public static Raid.RaiderType SKIRMISHER;

    public static final String LEGIONER_INTERNAL_NAME = "LEGIONER";
    public static final int[] LEGIONER_COUNT_IN_WAVES = {0, 0, 0, 0, 2, 0, 2, 3};
    public static Raid.RaiderType LEGIONER;
}
