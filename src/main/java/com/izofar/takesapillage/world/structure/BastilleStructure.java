package com.izofar.takesapillage.world.structure;

import com.izofar.takesapillage.init.ModStructures;
import com.izofar.takesapillage.util.ModStructureUtils;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

public class BastilleStructure extends PillagerStructure {

    public static final StructureSeparationSettings SEPARATION_SETTINGS = new StructureSeparationSettings(32, 12, 789775274);

    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig){
        return super.isFeatureChunk(chunkGenerator, biomeSource, seed, chunkRandom, chunkX, chunkZ, biome, chunkPos, featureConfig)
            && !ModStructureUtils.isNearStructure(chunkGenerator, ModStructures.PILLAGER_CAMP.get(), seed, chunkRandom, chunkX, chunkZ, 4);
    }

    @Override
    protected int getTerrainSearchRadius() {
        return 3;
    }

    @Override
    protected int getMaxTerrainRange() {
        return 8;
    }

    @Override
    protected String getStartPool() {
        return "bastille/start_pool";
    }

    @Override
    protected int getSize() {
        return 3;
    }

}
