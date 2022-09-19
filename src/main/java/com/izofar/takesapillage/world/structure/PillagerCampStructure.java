package com.izofar.takesapillage.world.structure;


import net.minecraft.world.gen.settings.StructureSeparationSettings;

public class PillagerCampStructure extends PillagerStructure {

    public static final StructureSeparationSettings SEPARATION_SETTINGS = new StructureSeparationSettings(16, 4, 10520565);

    @Override
    protected int getTerrainSearchRadius() {
        return 1;
    }

    @Override
    protected int getMaxTerrainRange() {
        return 4;
    }

    @Override
    protected String getStartPool() {
        return "pillager_camp/start_pool";
    }

    @Override
    protected int getSize() {
        return 6;
    }

}
