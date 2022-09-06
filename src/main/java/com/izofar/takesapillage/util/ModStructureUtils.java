package com.izofar.takesapillage.util;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;

public abstract class ModStructureUtils {

    public static boolean isNearStructure(ChunkGenerator chunk, long seed, ChunkPos inChunkPos, ResourceKey<StructureSet> feature, int radius) {
        return chunk.hasFeatureChunkInRange(feature, seed, inChunkPos.x, inChunkPos.z, radius);
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
}
