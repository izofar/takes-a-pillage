package com.izofar.takesapillage.init;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.world.structure.ModStructureUtils;
import com.izofar.takesapillage.world.structure.PillagerCampStructure;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class ModStructures {

    public static final DeferredRegister<StructureFeature<?>> MODDED_STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, TakesAPillageMod.MODID);

    public static final RegistryObject<StructureFeature<JigsawConfiguration>> PILLAGER_CAMP = MODDED_STRUCTURES.register("pillager_camp", () -> new PillagerCampStructure(JigsawConfiguration.CODEC));

    public static void setupStructures() {
        ModStructureUtils.setupMapSpacingAndLand((StructureFeature)PILLAGER_CAMP.get(), new StructureFeatureConfiguration(16, 4, 713742328), true);
    }

    public static void register(IEventBus eventBus) {
        MODDED_STRUCTURES.register(eventBus);
    }
}
