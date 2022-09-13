package com.izofar.takesapillage.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;

public abstract class ModStructureUtils {

    public static boolean isNearStructure(Structure.GenerationContext context, Holder<StructureSet> feature, int radius) {
        ChunkPos chunkPos = context.chunkPos();
        return context.chunkGenerator().hasStructureChunkInRange(feature, context.randomState(), context.seed(), chunkPos.x, chunkPos.z, radius);
    }

    public static boolean isRelativelyFlat(Structure.GenerationContext context, int chunkSearchRadius, int maxTerrainHeightVariation) {
        ChunkPos chunkpos = context.chunkPos();
        int maxTerrainHeight = Integer.MIN_VALUE;
        int minTerrainHeight = Integer.MAX_VALUE;
        for (int chunkX = chunkpos.x - chunkSearchRadius; chunkX <= chunkpos.x + chunkSearchRadius; chunkX++) {
            for (int chunkZ = chunkpos.z - chunkSearchRadius; chunkZ <= chunkpos.z + chunkSearchRadius; chunkZ++) {
                BlockPos blockpos = new BlockPos((chunkX << 4) + 7, 0, (chunkZ << 4) + 7);
                int height = context.chunkGenerator().getBaseHeight(blockpos.getX(), blockpos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
                maxTerrainHeight = Math.max(maxTerrainHeight, height);
                minTerrainHeight = Math.min(minTerrainHeight, height);
                if (!context.chunkGenerator().getBaseColumn(blockpos.getX(), blockpos.getZ(), context.heightAccessor(), context.randomState()).getBlock(height).getFluidState().isEmpty())
                    return false;
            }
        }
        return maxTerrainHeight - minTerrainHeight <= maxTerrainHeightVariation;
    }

    public static boolean isOnLand(Structure.GenerationContext context, int chunkSearchRadius){
        ChunkPos chunkpos = context.chunkPos();
        for(int chunkX = chunkpos.x - chunkSearchRadius; chunkX <= chunkpos.x + chunkSearchRadius; chunkX += chunkSearchRadius){
            for(int chunkZ = chunkpos.z - chunkSearchRadius; chunkZ <= chunkpos.z + chunkSearchRadius; chunkZ += chunkSearchRadius) {
                BlockPos centerOfChunk = chunkpos.getMiddleBlockPosition(0);
                int landHeight = context.chunkGenerator().getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
                NoiseColumn columnOfBlocks = context.chunkGenerator().getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ(), context.heightAccessor(), context.randomState());
                BlockState topBlock = columnOfBlocks.getBlock(centerOfChunk.getY() + landHeight);

                if (!topBlock.getFluidState().isEmpty())
                    return false;
            }
        }
        return true;
    }
}
