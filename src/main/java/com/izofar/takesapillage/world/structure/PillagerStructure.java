package com.izofar.takesapillage.world.structure;

import com.izofar.takesapillage.util.ModStructureUtils;
import com.izofar.takesapillage.world.configuration.ModJigsawConfiguration;
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
    private static final int STRUCTURE_SEARCH_RADIUS = 10;

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
        ModJigsawConfiguration config = ModJigsawConfiguration.getConfigFromJigsawContext(context);
        return !ModStructureUtils.isNearStructure(context.chunkGenerator(), context.seed(), context.chunkPos(), BuiltinStructureSets.VILLAGES, STRUCTURE_SEARCH_RADIUS)
                && !ModStructureUtils.isNearStructure(context.chunkGenerator(), context.seed(), context.chunkPos(), BuiltinStructureSets.PILLAGER_OUTPOSTS, STRUCTURE_SEARCH_RADIUS)
                && ModStructureUtils.isRelativelyFlat(context, config.getTerrainSearchRadius(), config.getMaxTerrainRange())
                && ModStructureUtils.isOnLand(context, config.getTerrainSearchRadius());
    }

    public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        if(!checkLocation(context)) return Optional.empty();
        BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(0);
        return JigsawPlacement.addPieces(context, PoolElementStructurePiece::new, blockpos, false, true);
    }

}
