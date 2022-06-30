package com.izofar.takesapillage.world.structure;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class ModStructureUtils {
    
    private static final Predicate<Block> isAir = (block -> (block == Blocks.AIR || block == Blocks.CAVE_AIR));

    public static PieceGeneratorSupplier.Context<JigsawConfiguration> duplicateContext(PieceGeneratorSupplier.Context<JigsawConfiguration> context, JigsawConfiguration config) {
        return new PieceGeneratorSupplier.Context(context.chunkGenerator(), context.biomeSource(), context.seed(), context.chunkPos(), config, context.heightAccessor(), context.validBiome(), context.structureManager(), context.registryAccess());
    }

    public static boolean isNearStructure(ChunkGenerator chunk, long seed, ChunkPos inChunkPos, StructureFeature<? extends FeatureConfiguration> feature) {
        StructureFeatureConfiguration structurefeatureconfiguration = chunk.getSettings().getConfig(feature);
        if (structurefeatureconfiguration != null) {
            int i = inChunkPos.x;
            int j = inChunkPos.z;
            for (int k = i - 10; k <= i + 10; k++) {
                for (int l = j - 10; l <= j + 10; l++) {
                    ChunkPos chunkpos = feature.getPotentialFeatureChunk(structurefeatureconfiguration, seed, k, l);
                    if (k == chunkpos.x && l == chunkpos.z)
                        return true;
                }
            }
        }
        return false;
    }

    public static boolean isRelativelyFlat(PieceGeneratorSupplier.Context<JigsawConfiguration> context, int chunk_search_radius, int max_terrain_height) {
        ChunkPos chunkpos = context.chunkPos();
        int maxterrainheight = Integer.MIN_VALUE;
        int minterrainheight = Integer.MAX_VALUE;
        for (int chunkX = chunkpos.x - chunk_search_radius; chunkX <= chunkpos.x + chunk_search_radius; chunkX++) {
            for (int chunkZ = chunkpos.z - chunk_search_radius; chunkZ <= chunkpos.z + chunk_search_radius; chunkZ++) {
                BlockPos blockpos = new BlockPos((chunkX << 4) + 7, 0, (chunkZ << 4) + 7);
                int height = context.chunkGenerator().getBaseHeight(blockpos.getX(), blockpos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());
                maxterrainheight = Math.max(maxterrainheight, height);
                minterrainheight = Math.min(minterrainheight, height);
                if (!context.chunkGenerator().getBaseColumn(blockpos.getX(), blockpos.getZ(), context.heightAccessor()).getBlock(height).getFluidState().isEmpty())
                    return false;
            }
        }
        return maxterrainheight - minterrainheight <= max_terrain_height;
    }

    public static <F extends StructureFeature<?>> void setupMapSpacingAndLand(F structure, StructureFeatureConfiguration config, boolean transformLand) {
        StructureFeature.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);
        if (transformLand)
            StructureFeature.NOISE_AFFECTING_FEATURES = ImmutableList.<StructureFeature<?>>builder().addAll(StructureFeature.NOISE_AFFECTING_FEATURES).add(structure).build();
        StructureSettings.DEFAULTS = ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder().putAll(StructureSettings.DEFAULTS).put(structure, config).build();
        BuiltinRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<StructureFeature<?>, StructureFeatureConfiguration> structureMap = settings.getValue().structureSettings().structureConfig();
            if (structureMap instanceof ImmutableMap) {
                Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, config);
                (settings.getValue().structureSettings()).structureConfig = tempMap;
            } else {
                structureMap.put(structure, config);
            }
        });
    }
}
