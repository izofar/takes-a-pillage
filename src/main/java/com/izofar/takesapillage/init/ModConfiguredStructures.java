package com.izofar.takesapillage.init;

import com.izofar.takesapillage.TakesAPillageMod;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.PlainVillagePools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;

public abstract class ModConfiguredStructures {

    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_PILLAGER_CAMP = ((StructureFeature)ModStructures.PILLAGER_CAMP.get()).configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));

    public static void registerConfiguredStructures() {
        Registry<ConfiguredStructureFeature<?, ?>> CONFIGURED_STRUCTURES = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(CONFIGURED_STRUCTURES, new ResourceLocation(TakesAPillageMod.MODID, "configured_pillager_camp"), CONFIGURED_PILLAGER_CAMP);
    }
}
