package com.izofar.takesapillage.world.structure;

import com.izofar.takesapillage.util.ModStructureUtils;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;

import java.util.Optional;

public class PillagerStructure extends StructureFeature<JigsawConfiguration> {

    private static final int CHUNK_SEARCH_RADIUS = 3;
    private static final int MAX_TERRAIN_RANGE = 10;
    
    public PillagerStructure(Codec<JigsawConfiguration> codec) { super(codec, PillagerStructure::createPiecesGenerator, PostPlacementProcessor.NONE); }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    private static boolean checkLocation(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        int i = context.chunkPos().x >> 4;
        int j = context.chunkPos().z >> 4;
        WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(0L));
        worldgenrandom.setSeed((i ^ j << 4) ^ context.seed());
        return !(worldgenrandom.nextInt(5) != 0
                || ModStructureUtils.isNearStructure(context.chunkGenerator(), context.seed(), context.chunkPos(), BuiltinStructureSets.VILLAGES, 10)
                || ModStructureUtils.isNearStructure(context.chunkGenerator(), context.seed(), context.chunkPos(), BuiltinStructureSets.PILLAGER_OUTPOSTS, 10))
                && ModStructureUtils.isRelativelyFlat(context, CHUNK_SEARCH_RADIUS, MAX_TERRAIN_RANGE)
                && ModStructureUtils.isOnLand(context, CHUNK_SEARCH_RADIUS);
    }

    public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        if(!checkLocation(context)) return Optional.empty();
        BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(0);
        return JigsawPlacement.addPieces(context, PoolElementStructurePiece::new, blockpos, false, true);
    }
}
