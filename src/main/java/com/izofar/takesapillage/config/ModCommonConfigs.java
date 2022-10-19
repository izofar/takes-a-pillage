package com.izofar.takesapillage.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Boolean> REPLACE_IRON_GOLEMS;
    public static final ForgeConfigSpec.ConfigValue<Boolean> REMOVE_BAD_OMEN;
    public static final ForgeConfigSpec.ConfigValue<Boolean> DO_PILLAGE_SIEGES;

    static {
        BUILDER.push("It Takes A Pillage - Config");

        REPLACE_IRON_GOLEMS = BUILDER.comment("Replace naturally spawning Iron Golems with Clay Golems?")
                .define("Replace Iron Golems", true);
        REMOVE_BAD_OMEN = BUILDER.comment("Remove Bad Omen effect after drinking milk?")
                .define("Milk Removes Bad Omen", false);
        DO_PILLAGE_SIEGES = BUILDER.comment("Pillage Sieges occur at night?")
                .define("Enable Pillage Sieges", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
