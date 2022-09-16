package com.izofar.takesapillage.world.structure;

import com.google.common.collect.ImmutableList;
import com.izofar.takesapillage.util.ModStructureUtils;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;

public class PillagerStructure extends Structure<VillageConfig> {

    private static final int STRUCTURE_SEARCH_RADIUS = 10;
    private static final int TERRAIN_SEARCH_RADIUS = 3;
    private static final int MAX_TERRAIN_RANGE = 10;
    private static final List<MobSpawnInfo.Spawners> PILLAGER_STRUCTURE_ENEMIES = ImmutableList.of();

    public static final List<String> VANILLA_SPAWNABLE_BIOMES = ImmutableList.of(
            "minecraft:plains",
            "minecraft:snowy_plains",
            "minecraft:meadow",
            "minecraft:savanna",
            "minecraft:sunflower_plains"
    );
    public static final List<String> BOP_SPAWNABLE_BIOMES = ImmutableList.of(
            "biomesoplenty:bog",
            "biomesoplenty:clover_patch",
            "biomesoplenty:dryland",
            "biomesoplenty:fir_clearing",
            "biomesoplenty:grassland",
            "biomesoplenty:lavender_field",
            "biomesoplenty:lush_savanna",
            "biomesoplenty:pasture",
            "biomesoplenty:prairie",
            "biomesoplenty:scrubland",
            "biomesoplenty:tundra"
    );
    public static final List<String> BYG_SPAWNABLE_BIOMES = ImmutableList.of(
            "byg:allium_fields",
            "byg:amaranth_fields",
            "byg:araucaria_savanna",
            "byg:baobab_savanna",
            "byg:cardinal_tundra",
            "byg:coconino_meadow",
            "byg:firecracker_shrubland",
            "byg:prairie",
            "byg:rose_fields"
    );

    public PillagerStructure() { super(VillageConfig.CODEC); }

    @Override
    public IStartFactory<VillageConfig> getStartFactory(){ return PillagerStructure.Start::new; }

    @Override
    public GenerationStage.Decoration step() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public List<MobSpawnInfo.Spawners> getDefaultSpawnList() { return PILLAGER_STRUCTURE_ENEMIES; }

    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, VillageConfig featureConfig){
        int x = chunkX * 16;
        int z = chunkZ * 16;
        return !ModStructureUtils.isNearStructure(chunkGenerator, Structure.VILLAGE, seed, chunkRandom, chunkX, chunkZ, STRUCTURE_SEARCH_RADIUS)
                && ! ModStructureUtils.isNearStructure(chunkGenerator, Structure.PILLAGER_OUTPOST, seed, chunkRandom, chunkX, chunkZ, STRUCTURE_SEARCH_RADIUS)
                && ModStructureUtils.isRelativelyFlat(chunkGenerator, chunkX, chunkZ, TERRAIN_SEARCH_RADIUS, MAX_TERRAIN_RANGE)
                && ModStructureUtils.isOnLand(chunkGenerator, chunkX, chunkZ, TERRAIN_SEARCH_RADIUS);
    }

    public static class Start extends StructureStart<VillageConfig> {
        public Start(Structure<VillageConfig> structure, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int reference, long seed) {
            super(structure, chunkX, chunkZ, mutableBoundingBox, reference, seed);
        }

        @Override
        public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biome, VillageConfig config){
            int x = chunkX * 16;
            int z = chunkZ * 16;
            BlockPos centerPos = new BlockPos(x, 0, z);

            JigsawManager.addPieces(
                    dynamicRegistryManager,
                    config,
                    AbstractVillagePiece::new,
                    chunkGenerator,
                    templateManagerIn,
                    centerPos,
                    this.pieces,
                    this.random,
                    false,
                    true);
        }
    }


}
