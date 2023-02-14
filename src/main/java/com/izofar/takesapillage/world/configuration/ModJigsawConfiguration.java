package com.izofar.takesapillage.world.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class ModJigsawConfiguration extends JigsawConfiguration {
    public static final Codec<JigsawConfiguration> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(ModJigsawConfiguration::castedStartPool),
                    Codec.intRange(0, 30).fieldOf("size").forGetter(ModJigsawConfiguration::castedSize),
                    Codec.intRange(0, 30).fieldOf("terrain_search_radius").forGetter(ModJigsawConfiguration::castedTerrainSearchRadius),
                    Codec.intRange(0, 30).fieldOf("max_terrain_range").forGetter(ModJigsawConfiguration::castedMaxTerrainHeight)
            ).apply(instance, ModJigsawConfiguration::new));

    private final Holder<StructureTemplatePool> startPool;
    private final int size;
    private final int terrainSearchRadius;
    private final int maxTerrainRange;

    public ModJigsawConfiguration(Holder<StructureTemplatePool> startPool,
                                  int size,
                                  int terrainSearchRadius,
                                  int maxTerrainHeight) {
        super(startPool, size);
        this.startPool = startPool;
        this.size = size;
        this.terrainSearchRadius = terrainSearchRadius;
        this.maxTerrainRange = maxTerrainHeight;
    }

    public int getTerrainSearchRadius(){
        return this.terrainSearchRadius;
    }

    public int getMaxTerrainRange(){
        return this.maxTerrainRange;
    }

    public static ModJigsawConfiguration getConfigFromJigsawContext(PieceGeneratorSupplier.Context<JigsawConfiguration> context){
        JigsawConfiguration config0 = context.config();
        if(context.config() instanceof ModJigsawConfiguration config)
            return config;
        else
            return new ModJigsawConfiguration(config0.startPool(), config0.maxDepth(), 0, 0);
    }

    public static Holder<StructureTemplatePool> castedStartPool(JigsawConfiguration config){
        if(config instanceof ModJigsawConfiguration config0)
            return config0.startPool;
        else return config.startPool();
    }

    public static int castedSize(JigsawConfiguration config){
        if(config instanceof ModJigsawConfiguration config0)
            return config0.size;
        else return config.maxDepth();
    }

    public static int castedTerrainSearchRadius(JigsawConfiguration config){
        if(config instanceof ModJigsawConfiguration config0)
            return config0.terrainSearchRadius;
        else return 0;
    }

    public static int castedMaxTerrainHeight(JigsawConfiguration config){
        if(config instanceof ModJigsawConfiguration config0)
            return config0.maxTerrainRange;
        else return 0;
    }
}
