package com.izofar.takesapillage.config;

import net.minecraft.world.Difficulty;
import net.minecraftforge.common.ForgeConfigSpec;

public class ModCommonConfigs {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final boolean DEFAULT_REPLACE_IRON_GOLEMS = true;
    public static final double DEFAULT_PEACEFUL_REPLACE_IRON_GOLEMS = 0.0;
    public static final double DEFAULT_EASY_REPLACE_IRON_GOLEMS = 0.2;
    public static final double DEFAULT_NORMAL_REPLACE_IRON_GOLEMS = 0.5;
    public static final double DEFAULT_HARD_REPLACE_IRON_GOLEMS = 1.0;
    public static final boolean DEFAULT_REMOVE_BAD_OMEN = false;
    public static final boolean DEFAULT_PILLAGE_SIEGES_OCCUR = true;

    public static final ForgeConfigSpec.ConfigValue<Boolean> REPLACE_IRON_GOLEMS;
    public static final ForgeConfigSpec.ConfigValue<Double> PEACEFUL_REPLACE_IRON_GOLEMS;
    public static final ForgeConfigSpec.ConfigValue<Double> EASY_REPLACE_IRON_GOLEMS;
    public static final ForgeConfigSpec.ConfigValue<Double> NORMAL_REPLACE_IRON_GOLEMS;
    public static final ForgeConfigSpec.ConfigValue<Double> HARD_REPLACE_IRON_GOLEMS;
    public static final ForgeConfigSpec.ConfigValue<Boolean> REMOVE_BAD_OMEN;
    public static final ForgeConfigSpec.ConfigValue<Boolean> PILLAGE_SIEGES_OCCUR;

    public static final String REPLACE_IRON_GOLEMS_LABEL = "Replace Iron Golems";
    public static final String REPLACE_IRON_GOLEMS_COMMENT = "Replace naturally spawning Iron Golems with Clay Golems?";
    public static final String PEACEFUL_REPLACE_IRON_GOLEMS_LABEL = "Peaceful Replacement Rate";
    public static final String PEACEFUL_REPLACE_IRON_GOLEMS_COMMENT = "Clay Golem replacement rate (Peaceful difficulty)";
    public static final String EASY_REPLACE_IRON_GOLEMS_LABEL = "Easy Replacement Rate";
    public static final String EASY_REPLACE_IRON_GOLEMS_COMMENT = "Clay Golem replacement rate (Easy difficulty)";
    public static final String NORMAL_REPLACE_IRON_GOLEMS_LABEL = "Normal Replacement Rate";
    public static final String NORMAL_REPLACE_IRON_GOLEMS_COMMENT = "Clay Golem replacement rate (Normal difficulty)";
    public static final String HARD_REPLACE_IRON_GOLEMS_LABEL = "Hard Replacement Rate";
    public static final String HARD_REPLACE_IRON_GOLEMS_COMMENT = "Clay Golem replacement rate (Hard difficulty)";
    public static final String REMOVE_BAD_OMEN_LABEL = "Milk Removes Bad Omen";
    public static final String REMOVE_BAD_OMEN_COMMENT = "Remove Bad Omen effect after drinking milk?";
    public static final String PILLAGE_SIEGES_OCCUR_LABEL = "Enable Pillage Sieges";
    public static final String PILLAGE_SIEGES_OCCUR_COMMENT = "Pillage Sieges occur at night?";

    static {
        BUILDER.push("It Takes A Pillage - Config");

        REPLACE_IRON_GOLEMS = BUILDER.comment(REPLACE_IRON_GOLEMS_COMMENT)
                .define(REPLACE_IRON_GOLEMS_LABEL, DEFAULT_REPLACE_IRON_GOLEMS);
        PEACEFUL_REPLACE_IRON_GOLEMS = BUILDER.comment(PEACEFUL_REPLACE_IRON_GOLEMS_COMMENT)
                .define(PEACEFUL_REPLACE_IRON_GOLEMS_LABEL, DEFAULT_PEACEFUL_REPLACE_IRON_GOLEMS);
        EASY_REPLACE_IRON_GOLEMS = BUILDER.comment(EASY_REPLACE_IRON_GOLEMS_COMMENT)
                .define(EASY_REPLACE_IRON_GOLEMS_LABEL, DEFAULT_EASY_REPLACE_IRON_GOLEMS);
        NORMAL_REPLACE_IRON_GOLEMS = BUILDER.comment(NORMAL_REPLACE_IRON_GOLEMS_COMMENT)
                .define(NORMAL_REPLACE_IRON_GOLEMS_LABEL, DEFAULT_NORMAL_REPLACE_IRON_GOLEMS);
        HARD_REPLACE_IRON_GOLEMS = BUILDER.comment(HARD_REPLACE_IRON_GOLEMS_COMMENT)
                .define(HARD_REPLACE_IRON_GOLEMS_LABEL, DEFAULT_HARD_REPLACE_IRON_GOLEMS);
        REMOVE_BAD_OMEN = BUILDER.comment(REMOVE_BAD_OMEN_COMMENT)
                .define(REMOVE_BAD_OMEN_LABEL, DEFAULT_REMOVE_BAD_OMEN);
        PILLAGE_SIEGES_OCCUR = BUILDER.comment(PILLAGE_SIEGES_OCCUR_COMMENT)
                .define(PILLAGE_SIEGES_OCCUR_LABEL, DEFAULT_PILLAGE_SIEGES_OCCUR);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }

    public static double getReplacementRate(Difficulty difficulty){
        switch (difficulty){
            case PEACEFUL:
                return PEACEFUL_REPLACE_IRON_GOLEMS.get();
            case EASY:
                return EASY_REPLACE_IRON_GOLEMS.get();
            case NORMAL:
                return NORMAL_REPLACE_IRON_GOLEMS.get();
            default: // HARD
                return HARD_REPLACE_IRON_GOLEMS.get();
        }
    }
}
