package com.izofar.takesapillage.init;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.world.structure.PillagerStructure;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class ModStructures {

    public static final DeferredRegister<StructureFeature<?>> MODDED_STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, TakesAPillageMod.MODID);

    public static final RegistryObject<StructureFeature<JigsawConfiguration>> PILLAGER_STRUCTURE = MODDED_STRUCTURES.register("pillager_structure", () -> new PillagerStructure(JigsawConfiguration.CODEC));
    //public static final RegistryObject<StructureFeature<JigsawConfiguration>> BASTILLE = MODDED_STRUCTURES.register("bastille", () -> new BastilleStructure(JigsawConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        MODDED_STRUCTURES.register(eventBus);
    }
}
