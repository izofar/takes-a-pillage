package com.izofar.takesapillage.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.izofar.takesapillage.TakesAPillageMod;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

import java.util.HashMap;
import java.util.Map;

public abstract class ModStructureUtils {

    public static boolean isNearStructure(ChunkGenerator chunkGenerator, Structure<?> structure, long seed, SharedSeedRandom random, int chunkX, int chunkZ, int radius) {
        StructureSeparationSettings structureseparationsettings = chunkGenerator.getSettings().getConfig(structure);
        if (structureseparationsettings != null) {
            for (int i = chunkX - radius; i <= chunkX + radius; ++i) {
                for (int j = chunkZ - radius; j <= chunkZ + radius; ++j) {
                    ChunkPos chunkpos = structure.getPotentialFeatureChunk(structureseparationsettings, seed, random, i, j);
                    if (i == chunkpos.x && j == chunkpos.z) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public static boolean isRelativelyFlat(ChunkGenerator chunkGenerator, int chunkX, int chunkZ, int chunkSearchRadius, int maxTerrainHeightVariation) {
        int maxTerrainHeight = Integer.MIN_VALUE;
        int minTerrainHeight = Integer.MAX_VALUE;
        for (int x = chunkX - chunkSearchRadius; x <= chunkX + chunkSearchRadius; x += chunkSearchRadius) {
            for (int z = chunkZ - chunkSearchRadius; z <= chunkZ + chunkSearchRadius; z += chunkSearchRadius) {
                BlockPos blockpos = new BlockPos((x << 4) + 7, 0, (z << 4) + 7);
                int height = chunkGenerator.getFirstOccupiedHeight(blockpos.getX(), blockpos.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
                maxTerrainHeight = Math.max(maxTerrainHeight, height);
                minTerrainHeight = Math.min(minTerrainHeight, height);
                if (!chunkGenerator.getBaseColumn(blockpos.getX(), blockpos.getZ()).getBlockState(blockpos.above(height)).getFluidState().isEmpty())
                    return false;
                if(maxTerrainHeight - minTerrainHeight >= maxTerrainHeightVariation)
                    return false;
            }
        }
        return maxTerrainHeight - minTerrainHeight <= maxTerrainHeightVariation;
    }

    public static boolean isOnLand(ChunkGenerator chunkGenerator, int chunkX, int chunkZ, int chunkSearchRadius){
        return isDryChunkCenter(chunkGenerator, chunkX - chunkSearchRadius, chunkZ - chunkSearchRadius)
                && isDryChunkCenter(chunkGenerator,chunkX - chunkSearchRadius, chunkZ + chunkSearchRadius)
                && isDryChunkCenter(chunkGenerator,chunkX + chunkSearchRadius, chunkZ - chunkSearchRadius)
                && isDryChunkCenter(chunkGenerator,chunkX + chunkSearchRadius, chunkZ + chunkSearchRadius);
    }

    private static boolean isDryChunkCenter(ChunkGenerator chunkGenerator, int chunkX, int chunkZ){
        BlockPos centerOfChunk = new BlockPos(chunkX * 16, 0, chunkZ * 16);
        int landHeight = chunkGenerator.getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
        IBlockReader columnOfBlocks = chunkGenerator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ());
        BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(landHeight));
        return topBlock.getFluidState().isEmpty();
    }

    public static VillageConfig createConfig(DynamicRegistries dynamicRegistryManager, String location, int size){
        return new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(new ResourceLocation(TakesAPillageMod.MODID, location)), size);
    }

    public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure, StructureSeparationSettings structureSeparationSettings, boolean transformLand) {
        Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

        if(transformLand) Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>>builder().addAll(Structure.NOISE_AFFECTING_FEATURES).add(structure).build();

        DimensionStructuresSettings.DEFAULTS = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder().putAll(DimensionStructuresSettings.DEFAULTS).put(structure, structureSeparationSettings).build();

        WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().structureSettings().structureConfig();
            if(structureMap instanceof ImmutableMap){
                Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, structureSeparationSettings);
                settings.getValue().structureSettings().structureConfig = tempMap;
            } else structureMap.put(structure, structureSeparationSettings);
        });
    }
}
