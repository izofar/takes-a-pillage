package com.izofar.takesapillage.world.structure;

import com.google.common.collect.ImmutableList;
import com.izofar.takesapillage.TakesAPillageMod;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;

import java.util.List;
import java.util.Optional;

public class PillagerCampStructure extends StructureFeature<JigsawConfiguration> {

    private static final int CHUNK_SEARCH_RADIUS = 3;
    private static final int MAX_TERRAIN_RANGE = 10;

    public static final List<ResourceKey<Biome>> PILLAGER_CAMP_BIOME_KEYS = ImmutableList.of(Biomes.PLAINS, Biomes.SNOWY_PLAINS, Biomes.MEADOW, Biomes.SAVANNA, Biomes.SUNFLOWER_PLAINS);

    public PillagerCampStructure(Codec<JigsawConfiguration> codec) { super(codec, PillagerCampStructure::checkLocation); }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    private static Optional<PieceGenerator<JigsawConfiguration>> checkLocation(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        int i = context.chunkPos().x >> 4;
        int j = context.chunkPos().z >> 4;
        WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(0L));
        worldgenrandom.setSeed((i ^ j << 4) ^ context.seed());
        if (worldgenrandom.nextInt(5) != 0
                || ModStructureUtils.isNearStructure(context.chunkGenerator(), context.seed(), context.chunkPos(), StructureFeature.VILLAGE)
                || ModStructureUtils.isNearStructure(context.chunkGenerator(), context.seed(), context.chunkPos(), StructureFeature.PILLAGER_OUTPOST))
            return Optional.empty();
        if (ModStructureUtils.isRelativelyFlat(context, CHUNK_SEARCH_RADIUS, MAX_TERRAIN_RANGE))
            return createPiecesGenerator(context);
        return Optional.empty();
    }

    public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(0);
        JigsawConfiguration newConfig = new JigsawConfiguration(() -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(new ResourceLocation(TakesAPillageMod.MODID, "pillager_camp/start_pool")), 6);
        return JigsawPlacement.addPieces(ModStructureUtils.duplicateContext(context, newConfig), net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece::new, blockpos, false, true);
    }
}
