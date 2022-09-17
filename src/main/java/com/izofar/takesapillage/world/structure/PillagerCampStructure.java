package com.izofar.takesapillage.world.structure;


public class PillagerCampStructure extends PillagerStructure {

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
