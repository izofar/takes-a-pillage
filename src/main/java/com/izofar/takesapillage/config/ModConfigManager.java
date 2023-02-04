package com.izofar.takesapillage.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.izofar.takesapillage.TakesAPillageMod;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import org.apache.commons.lang3.tuple.Pair;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class ModConfigManager {

    private static final ModConfigManager INSTANCE;
    private static final ForgeConfigSpec SPEC;
    private static final Path PATH = Paths.get("config", TakesAPillageMod.MODID + "-common.toml");

    static {
        Pair<ModConfigManager, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ModConfigManager::new);
        INSTANCE = specPair.getLeft();
        SPEC = specPair.getRight();
        CommentedFileConfig config = CommentedFileConfig.builder(PATH)
                .sync()
                .autoreload()
                .writingMode(WritingMode.REPLACE)
                .build();
        config.load();
        config.save();
        SPEC.setConfig(config);
    }

    private final BooleanValue replaceIronGolems;
    private final DoubleValue peacefulSpawnRate;
    private final DoubleValue easySpawnRate;
    private final DoubleValue normalSpawnRate;
    private final DoubleValue hardSpawnRate;
    private final BooleanValue removeBadOmen;
    private final BooleanValue pillageSiegesOccur;

    private ModConfigManager(ForgeConfigSpec.Builder configSpecBuilder){
        replaceIronGolems = configSpecBuilder
                .translation("takesapillage.configGui.replaceIronGolems.title")
                .define("Replace Iron Golems", ModCommonConfigs.REPLACE_IRON_GOLEMS);
        peacefulSpawnRate = configSpecBuilder
                .translation("takesapillage.configGui.peacefulGolemRate.title")
                .defineInRange("Peaceful Replacement Rate",
                        ModCommonConfigs.PEACEFUL_REPLACE_IRON_GOLEMS,
                        0.0, 1.0);
        easySpawnRate = configSpecBuilder
                .translation("takesapillage.configGui.easyGolemRate.title")
                .defineInRange("Easy Replacement Rate",
                        ModCommonConfigs.EASY_REPLACE_IRON_GOLEMS,
                        0.0, 1.0);
        normalSpawnRate = configSpecBuilder
                .translation("takesapillage.configGui.normalGolemRate.title")
                .defineInRange("Normal Replacement Rate",
                        ModCommonConfigs.NORMAL_REPLACE_IRON_GOLEMS,
                        0.0, 1.0);
        hardSpawnRate = configSpecBuilder
                .translation("takesapillage.configGui.hardGolemRate.title")
                .defineInRange("Hard Replacement Rate",
                        ModCommonConfigs.HARD_REPLACE_IRON_GOLEMS,
                        0.0, 1.0);
        removeBadOmen = configSpecBuilder
                .translation("takesapillage.configGui.removeBadOmen.title")
                .define("Milk Removes Bad Omen", ModCommonConfigs.REMOVE_BAD_OMEN);
        pillageSiegesOccur = configSpecBuilder
                .translation("takesapillage.configGui.pillageSiegesOccur.title")
                .define("Enable Pillage Sieges", ModCommonConfigs.DO_PILLAGE_SIEGES);
    }

    public static ModConfigManager getInstance(){
        return INSTANCE;
    }

    public boolean getReplaceIronGolems(){
        return replaceIronGolems.get();
    }

    public void setReplaceIronGolems(boolean value){
        replaceIronGolems.set(value);
    }

    public Integer getPeacefulReplaceIronGolems(){
        return (int)(peacefulSpawnRate.get() * 100);
    }

    public void setPeacefulReplaceIronGolems(Integer value){
        peacefulSpawnRate.set(value.doubleValue() / 100);
    }

    public Integer getEasyReplaceIronGolems(){
        return (int)(easySpawnRate.get() * 100);
    }

    public void setEasyReplaceIronGolems(Integer value){
        easySpawnRate.set(value.doubleValue() / 100);
    }

    public Integer getNormalReplaceIronGolems(){
        return (int)(normalSpawnRate.get() * 100);
    }

    public void setNormalReplaceIronGolems(Integer value){
        normalSpawnRate.set(value.doubleValue() / 100);
    }

    public Integer getHardReplaceIronGolems(){
        return (int)(hardSpawnRate.get() * 100);
    }

    public void setHardReplaceIronGolems(Integer value){
        hardSpawnRate.set(value.doubleValue() / 100);
    }

    public boolean getRemoveBadOmen(){
        return removeBadOmen.get();
    }

    public void setRemoveBadOmen(boolean value){
        removeBadOmen.set(value);
    }

    public boolean getPillageSiegesOccur(){
        return pillageSiegesOccur.get();
    }

    public void setPillageSiegesOccur(boolean value){
        pillageSiegesOccur.set(value);
    }

    public void save(){
        ModCommonConfigs.REPLACE_IRON_GOLEMS.set(replaceIronGolems.get());
        ModCommonConfigs.PEACEFUL_REPLACE_IRON_GOLEMS.set(peacefulSpawnRate.get());
        ModCommonConfigs.EASY_REPLACE_IRON_GOLEMS.set(easySpawnRate.get());
        ModCommonConfigs.NORMAL_REPLACE_IRON_GOLEMS.set(normalSpawnRate.get());
        ModCommonConfigs.HARD_REPLACE_IRON_GOLEMS.set(hardSpawnRate.get());
        ModCommonConfigs.REMOVE_BAD_OMEN.set(removeBadOmen.get());
        ModCommonConfigs.DO_PILLAGE_SIEGES.set(pillageSiegesOccur.get());
    }
}
