package com.izofar.takesapillage.entity.event;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.init.ModConfiguredStructures;
import com.izofar.takesapillage.init.ModStructures;
import com.izofar.takesapillage.world.PillageSiege;
import com.izofar.takesapillage.world.structure.PillagerCampStructure;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.*;

public abstract class ModWorldEvents {
    private static final PillageSiege siege = new PillageSiege();

    @SubscribeEvent
    public static void onSpecialSpawn(TickEvent.WorldTickEvent event) {
        Level level = event.world;
        if (level instanceof ServerLevel serverWorld) {
            siege.tick(serverWorld, true, false);
        }
    }

    @SubscribeEvent
    public static void addDimensionSpacing(WorldEvent.Load event) {
        LevelAccessor levelAccessor = event.getWorld();
        if (levelAccessor instanceof ServerLevel serverLevel) {
            
            ChunkGenerator chunkGenerator = serverLevel.getChunkSource().getGenerator();
            
            if (chunkGenerator instanceof FlatLevelSource && serverLevel.dimension().equals(Level.OVERWORLD))
                return;
            
            HashMap<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> multimap = new HashMap<>();
            Set<Map.Entry<ResourceKey<Biome>, Biome>> entrySet = serverLevel.registryAccess().ownedRegistryOrThrow(Registry.BIOME_REGISTRY).entrySet();
            List<Biome> pillager_camp_biomes = PillagerCampStructure.PILLAGER_CAMP_BIOME_KEYS.stream().map(key -> getBiomeFromEntrySet(entrySet, key)).toList();
            for (Map.Entry<ResourceKey<Biome>, Biome> biomeEntry : entrySet) {
                Biome biome = biomeEntry.getValue();
                ResourceKey<Biome> biomeKey = biomeEntry.getKey();
                if (pillager_camp_biomes.contains(biome))
                    associateBiomeToConfiguredStructure(multimap, ModConfiguredStructures.CONFIGURED_PILLAGER_CAMP, biomeKey);
            }
            ImmutableMap.Builder<StructureFeature<?>, ImmutableMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> tempStructureToMultiMap = ImmutableMap.builder();
            StructureSettings structureSettings = chunkGenerator.getSettings();
            Objects.requireNonNull(tempStructureToMultiMap);
            multimap.forEach((key, value) -> tempStructureToMultiMap.put(key, ImmutableMultimap.copyOf(value)));
            structureSettings.configuredStructures = tempStructureToMultiMap.build();
            Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureSettings.structureConfig());
            tempMap.putIfAbsent(ModStructures.PILLAGER_CAMP.get(), StructureSettings.DEFAULTS.get(ModStructures.PILLAGER_CAMP.get()));
            structureSettings.structureConfig = tempMap;
        }
    }

    private static void associateBiomeToConfiguredStructure(Map<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> multimap, ConfiguredStructureFeature<?, ?> configuredStructureFeature, ResourceKey<Biome> biomeRegistryKey) {
        multimap.putIfAbsent(configuredStructureFeature.feature, HashMultimap.create());
        HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>> configuredStructureToBiomeMultiMap = multimap.get(configuredStructureFeature.feature);
        if (configuredStructureToBiomeMultiMap.containsValue(biomeRegistryKey)) {
            TakesAPillageMod.LOGGER.error("    2 ConfiguredStructureFeatures sharing a StructureFeature were be added to the same biome.\n    One will be prevented from spawning.\n    The two conflicting ConfiguredStructures are: {}, {}\n    The biome that is attempting to be shared: {}\n",

                    BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(configuredStructureFeature),
                    BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId((configuredStructureToBiomeMultiMap
                            .entries()
                            .stream()
                            .filter(e -> (e.getValue() == biomeRegistryKey))
                            .findFirst()
                            .get())
                            .getKey()), biomeRegistryKey);
        } else
            configuredStructureToBiomeMultiMap.put(configuredStructureFeature, biomeRegistryKey);
    }

    private static Biome getBiomeFromEntrySet(Set<Map.Entry<ResourceKey<Biome>, Biome>> entrySet, ResourceKey<Biome> biome) {
        for (Map.Entry<ResourceKey<Biome>, Biome> biomeEntry : entrySet) {
            if ((biomeEntry.getKey()).equals(biome))
                return biomeEntry.getValue();
        }
        return null;
    }
}
