package com.izofar.takesapillage.config;

public final class ModConfigManager {

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

    public boolean getReplaceIronGolems(){
        return replaceIronGolems;
    }

    public void setReplaceIronGolems(boolean value){
        replaceIronGolems = value;
    }

    public Integer getPeacefulReplaceIronGolems(){
        return (int)(peacefulSpawnRate * 100);
    }

    public void setPeacefulReplaceIronGolems(Integer value){
        peacefulSpawnRate = value.doubleValue() / 100;
    }

    public Integer getEasyReplaceIronGolems(){
        return (int)(easySpawnRate * 100);
    }

    public void setEasyReplaceIronGolems(Integer value){
        easySpawnRate = value.doubleValue() / 100;
    }

    public Integer getNormalReplaceIronGolems(){
        return (int)(normalSpawnRate * 100);
    }

    public void setNormalReplaceIronGolems(Integer value){
        normalSpawnRate = value.doubleValue() / 100;
    }

    public Integer getHardReplaceIronGolems(){
        return (int)(hardSpawnRate * 100);
    }

    public void setHardReplaceIronGolems(Integer value){
        hardSpawnRate = value.doubleValue() / 100;
    }

    public boolean getRemoveBadOmen(){
        return removeBadOmen;
    }

    public void setRemoveBadOmen(boolean value){
        removeBadOmen = value;
    }

    public boolean getPillageSiegesOccur(){
        return pillageSiegesOccur;
    }

    public void setPillageSiegesOccur(boolean value){
        pillageSiegesOccur = value;
    }

    public void save(){
        ModCommonConfigs.REPLACE_IRON_GOLEMS.set(replaceIronGolems);
        ModCommonConfigs.PEACEFUL_REPLACE_IRON_GOLEMS.set(peacefulSpawnRate);
        ModCommonConfigs.EASY_REPLACE_IRON_GOLEMS.set(easySpawnRate);
        ModCommonConfigs.NORMAL_REPLACE_IRON_GOLEMS.set(normalSpawnRate);
        ModCommonConfigs.HARD_REPLACE_IRON_GOLEMS.set(hardSpawnRate);
        ModCommonConfigs.REMOVE_BAD_OMEN.set(removeBadOmen);
        ModCommonConfigs.PILLAGE_SIEGES_OCCUR.set(pillageSiegesOccur);
    }
}
