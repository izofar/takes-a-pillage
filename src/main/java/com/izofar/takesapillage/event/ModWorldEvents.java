package com.izofar.takesapillage.event;

import com.izofar.takesapillage.init.ModConfiguredStructures;
import com.izofar.takesapillage.init.ModEntityTypes;
import com.izofar.takesapillage.init.ModStructures;
import com.izofar.takesapillage.world.PillageSiege;
import com.izofar.takesapillage.world.structure.PillagerStructure;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ModWorldEvents {

    public static final PillageSiege PILLAGE_SIEGE = new PillageSiege();

    @SubscribeEvent
    public static void onSpecialSpawn(TickEvent.WorldTickEvent event) {
        World level = event.world;
        if (level.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING)
                && level instanceof ServerWorld
                && level.dimension() == World.OVERWORLD) {
            PILLAGE_SIEGE.tick((ServerWorld) level, true, false);
        }
    }

    public static void addModdedRaiders() {
        Raid.WaveMember.create("ARCHER", ModEntityTypes.ARCHER.get(), new int[]{0, 2, 0, 0, 2, 3, 2, 4});
        Raid.WaveMember.create("SKIRMISHER", ModEntityTypes.SKIRMISHER.get(), new int[]{0, 0, 0, 2, 0, 1, 1, 2});
        Raid.WaveMember.create("LEGIONER", ModEntityTypes.LEGIONER.get(), new int[]{0, 0, 0, 0, 2, 0, 2, 3});
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void addFeaturesToBiomes(final BiomeLoadingEvent event){

        boolean addToVanilla = hasBiome(event, PillagerStructure.VANILLA_SPAWNABLE_BIOMES);
        boolean addToBOP = isLoaded("biomesoplenty") && hasBiome(event, PillagerStructure.BOP_SPAWNABLE_BIOMES);
        boolean addToBYG = isLoaded("byg") && hasBiome(event, PillagerStructure.BYG_SPAWNABLE_BIOMES);

        if(addToVanilla || addToBOP || addToBYG)
            addPillagerStructures(event);

    }

    private static boolean isLoaded(String modid){ return ModList.get().isLoaded(modid); }
    private static boolean isBiome(final BiomeLoadingEvent event, String key){ return event.getName().toString().equals(key); }
    private static boolean hasBiome(final BiomeLoadingEvent event, List<String> keys){
        for(String key : keys)
            if(isBiome(event, key)) return true;
        return false;
    }

    private static void addPillagerStructures(final BiomeLoadingEvent event){
        event.getGeneration().getStructures().add(() -> ModConfiguredStructures.CONFIGURED_BASTILLE);
        event.getGeneration().getStructures().add(() -> ModConfiguredStructures.CONFIGURED_PILLAGER_CAMP);
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void addDimensionSpacing(final WorldEvent.Load event) {
        if (event.getWorld() instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) event.getWorld();
            ChunkGenerator chunkGenerator = serverWorld.getChunkSource().getGenerator();

            if (chunkGenerator instanceof FlatChunkGenerator && serverWorld.dimension().equals(World.OVERWORLD)) return;

            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());
            tempMap.putIfAbsent(ModStructures.PILLAGER_STRUCTURE.get(), DimensionStructuresSettings.DEFAULTS.get(ModStructures.PILLAGER_STRUCTURE.get()));
            chunkGenerator.getSettings().structureConfig = tempMap;
        }
    }
}
