package com.izofar.takesapillage.init;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.world.structure.PillagerStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public abstract class ModStructures {

    public static final DeferredRegister<StructureType<?>> MODDED_STRUCTURES = DeferredRegister.create(Registries.STRUCTURE_TYPE, TakesAPillageMod.MODID);

    public static final RegistryObject<StructureType<PillagerStructure>> PILLAGER_STRUCTURE = MODDED_STRUCTURES.register("pillager_structure", () -> () -> PillagerStructure.CODEC);

    public static void register(IEventBus eventBus) {
        MODDED_STRUCTURES.register(eventBus);
    }
}
