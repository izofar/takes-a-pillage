package com.izofar.takesapillage.config;

import net.minecraft.client.GameSettings;

public class ModConfigManager {

    private static final ModConfigManager INSTANCE;

    private boolean replaceIronGolems;
    private double peacefulSpawnRate;
    private double easySpawnRate;
    private double normalSpawnRate;
    private double hardSpawnRate;
    private boolean removeBadOmen;
    private boolean pillageSiegesOccur;

    static{
        INSTANCE = new ModConfigManager();
    }

    private ModConfigManager(){
        replaceIronGolems = ModCommonConfigs.REPLACE_IRON_GOLEMS.get();
        peacefulSpawnRate = ModCommonConfigs.PEACEFUL_REPLACE_IRON_GOLEMS.get();
        easySpawnRate = ModCommonConfigs.EASY_REPLACE_IRON_GOLEMS.get();
        normalSpawnRate = ModCommonConfigs.NORMAL_REPLACE_IRON_GOLEMS.get();
        hardSpawnRate = ModCommonConfigs.HARD_REPLACE_IRON_GOLEMS.get();
        removeBadOmen = ModCommonConfigs.REMOVE_BAD_OMEN.get();
        pillageSiegesOccur = ModCommonConfigs.PILLAGE_SIEGES_OCCUR.get();
    }

    public static ModConfigManager getInstance(){
        return INSTANCE;
    }

    public boolean getReplaceIronGolems(GameSettings options){
        return replaceIronGolems;
    }

    public void setReplaceIronGolems(GameSettings options, boolean value){
        replaceIronGolems = value;
    }

    public Double getPeacefulReplaceIronGolems(GameSettings options){
        return peacefulSpawnRate;
    }

    public void setPeacefulReplaceIronGolems(GameSettings options, double value){
        peacefulSpawnRate = value;
    }

    public Double getEasyReplaceIronGolems(GameSettings options){
        return easySpawnRate;
    }

    public void setEasyReplaceIronGolems(GameSettings options, double value){
        easySpawnRate = value;
    }

    public Double getNormalReplaceIronGolems(GameSettings options){
        return normalSpawnRate;
    }

    public void setNormalReplaceIronGolems(GameSettings options, double value){
        normalSpawnRate = value;
    }

    public Double getHardReplaceIronGolems(GameSettings options){
        return hardSpawnRate;
    }

    public void setHardReplaceIronGolems(GameSettings options, double value){
        hardSpawnRate = value;
    }

    public boolean getRemoveBadOmen(GameSettings options){
        return removeBadOmen;
    }

    public void setRemoveBadOmen(GameSettings options, boolean value){
        removeBadOmen = value;
    }

    public boolean getPillageSiegesOccur(GameSettings options){
        return pillageSiegesOccur;
    }

    public void setPillageSiegesOccur(GameSettings options, boolean value){
        pillageSiegesOccur = value;
    }

    public void save(){
        roundValues();
        ModCommonConfigs.REPLACE_IRON_GOLEMS.set(replaceIronGolems);
        ModCommonConfigs.PEACEFUL_REPLACE_IRON_GOLEMS.set(peacefulSpawnRate);
        ModCommonConfigs.EASY_REPLACE_IRON_GOLEMS.set(easySpawnRate);
        ModCommonConfigs.NORMAL_REPLACE_IRON_GOLEMS.set(normalSpawnRate);
        ModCommonConfigs.HARD_REPLACE_IRON_GOLEMS.set(hardSpawnRate);
        ModCommonConfigs.REMOVE_BAD_OMEN.set(removeBadOmen);
        ModCommonConfigs.PILLAGE_SIEGES_OCCUR.set(pillageSiegesOccur);
    }

    private void roundValues(){
        peacefulSpawnRate = Math.round(peacefulSpawnRate * 100) / 100.0D;
        easySpawnRate = Math.round(easySpawnRate * 100) / 100.0D;
        normalSpawnRate = Math.round(normalSpawnRate * 100) / 100.0D;
        hardSpawnRate = Math.round(hardSpawnRate * 100) / 100.0D;
    }
}
