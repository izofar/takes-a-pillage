package com.izofar.takesapillage.config;

import eu.midnightdust.lib.config.MidnightConfig;

public class ModCommonConfigs extends MidnightConfig {
    @MidnightConfig.Entry public static boolean REPLACE_IRON_GOLEMS = true;
    @MidnightConfig.Entry public static boolean REMOVE_BAD_OMEN = false;
    @MidnightConfig.Entry public static boolean DO_PILLAGE_SIEGES = true;
}
