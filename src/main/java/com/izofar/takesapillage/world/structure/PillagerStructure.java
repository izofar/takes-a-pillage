package com.izofar.takesapillage.world.structure;

import com.google.common.collect.ImmutableList;
import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.util.ModStructureUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

import java.util.List;

public abstract class PillagerStructure extends Structure<NoFeatureConfig> {

    protected static final int STRUCTURE_SEARCH_RADIUS = 10;
    public static final StructureSeparationSettings SEPARATION_SETTINGS = new StructureSeparationSettings(16, 4, 10520565);
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

    public PillagerStructure() { super(NoFeatureConfig.CODEC); }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory(){
        return (p0, p1, p2, p3, p4, p5) -> new PillagerStructure.Start(p0, p1, p2, p3, p4, p5, this.getStartPool(), this.getSize());
    }

    @Override
    public GenerationStage.Decoration step() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig){
        return !ModStructureUtils.isNearStructure(chunkGenerator, Structure.VILLAGE, seed, chunkRandom, chunkX, chunkZ, STRUCTURE_SEARCH_RADIUS)
                && ! ModStructureUtils.isNearStructure(chunkGenerator, Structure.PILLAGER_OUTPOST, seed, chunkRandom, chunkX, chunkZ, STRUCTURE_SEARCH_RADIUS)
                && ModStructureUtils.isRelativelyFlat(chunkGenerator, chunkX, chunkZ, this.getTerrainSearchRadius(), this.getMaxTerrainRange())
                && ModStructureUtils.isOnLand(chunkGenerator, chunkX, chunkZ, this.getTerrainSearchRadius());
    }

    protected abstract int getTerrainSearchRadius();

    protected abstract int getMaxTerrainRange();

    protected abstract String getStartPool();

    protected abstract int getSize();

    public static class Start extends StructureStart<NoFeatureConfig> {

        private final String startPool;
        private final int size;

        public Start(Structure<NoFeatureConfig> structure, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int reference, long seed, String startPool, int size) {
            super(structure, chunkX, chunkZ, mutableBoundingBox, reference, seed);
            this.startPool = startPool;
            this.size = size;
        }

        @Override
        public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biome, NoFeatureConfig config){
            int x = chunkX * 16;
            int z = chunkZ * 16;
            BlockPos centerPos = new BlockPos(x, 0, z);

            JigsawManager.addPieces(
                    dynamicRegistryManager,
                    //ModStructureUtils.createConfig(dynamicRegistryManager, this.startPool, this.size),
                    new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(new ResourceLocation(TakesAPillageMod.MODID, "pillager_camp/start_pool")), 6),
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
