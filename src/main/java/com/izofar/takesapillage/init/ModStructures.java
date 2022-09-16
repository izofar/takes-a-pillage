package com.izofar.takesapillage.init;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.world.structure.PillagerStructure;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class ModStructures {

    public static final DeferredRegister<Structure<?>> MODDED_STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, TakesAPillageMod.MODID);

    public static final RegistryObject<Structure<VillageConfig>> PILLAGER_STRUCTURE = MODDED_STRUCTURES.register("pillager_structure", PillagerStructure::new);

    public static void register(IEventBus eventBus) {
        MODDED_STRUCTURES.register(eventBus);
    }
}
