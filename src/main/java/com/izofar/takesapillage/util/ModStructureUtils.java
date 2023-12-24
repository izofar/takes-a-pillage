package com.izofar.takesapillage.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;

public abstract class ModStructureUtils {

    public static boolean isRelativelyFlat(Structure.GenerationContext context, int chunkSearchRadius, int maxTerrainHeightVariation) {
        ChunkPos chunkpos = context.chunkPos();
        int maxTerrainHeight = Integer.MIN_VALUE;
        int minTerrainHeight = Integer.MAX_VALUE;
        for (int chunkX = chunkpos.x - chunkSearchRadius; chunkX <= chunkpos.x + chunkSearchRadius; chunkX += chunkSearchRadius) {
            for (int chunkZ = chunkpos.z - chunkSearchRadius; chunkZ <= chunkpos.z + chunkSearchRadius; chunkZ += chunkSearchRadius) {
                BlockPos blockpos = new BlockPos((chunkX << 4) + 7, 0, (chunkZ << 4) + 7);
                int height = context.chunkGenerator().getBaseHeight(blockpos.getX(), blockpos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
                maxTerrainHeight = Math.max(maxTerrainHeight, height);
                minTerrainHeight = Math.min(minTerrainHeight, height);
                if (!context.chunkGenerator().getBaseColumn(blockpos.getX(), blockpos.getZ(), context.heightAccessor(), context.randomState()).getBlock(height).getFluidState().isEmpty())
                    return false;
                if(maxTerrainHeight - minTerrainHeight >= maxTerrainHeightVariation)
                    return false;
            }
        }
        return maxTerrainHeight - minTerrainHeight <= maxTerrainHeightVariation;
    }

    public static boolean isOnLand(Structure.GenerationContext context, int chunkSearchRadius){
        ChunkPos chunkpos = context.chunkPos();
        return isDryChunkCenter(context, new ChunkPos(chunkpos.x - chunkSearchRadius, chunkpos.z - chunkSearchRadius))
                && isDryChunkCenter(context, new ChunkPos(chunkpos.x - chunkSearchRadius, chunkpos.z + chunkSearchRadius))
                && isDryChunkCenter(context, new ChunkPos(chunkpos.x + chunkSearchRadius, chunkpos.z - chunkSearchRadius))
                && isDryChunkCenter(context, new ChunkPos(chunkpos.x + chunkSearchRadius, chunkpos.z + chunkSearchRadius));
    }

    private static boolean isDryChunkCenter(Structure.GenerationContext context, ChunkPos chunkPos){
        BlockPos centerOfChunk = chunkPos.getMiddleBlockPosition(0);
        int landHeight = context.chunkGenerator().getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
        NoiseColumn columnOfBlocks = context.chunkGenerator().getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ(), context.heightAccessor(), context.randomState());
        BlockState topBlock = columnOfBlocks.getBlock(centerOfChunk.getY() + landHeight);
        return topBlock.getFluidState().isEmpty();
    }
}
