package com.izofar.takesapillage.init;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.util.ModStructureUtils;
import com.izofar.takesapillage.world.structure.PillagerCampStructure;
import com.izofar.takesapillage.world.structure.PillagerStructure;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class ModStructures {

    public static final DeferredRegister<Structure<?>> MODDED_STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, TakesAPillageMod.MODID);

    //public static final RegistryObject<Structure<NoFeatureConfig>> BASTILLE = MODDED_STRUCTURES.register("bastille", BastilleStructure::new);
    public static final RegistryObject<Structure<NoFeatureConfig>> PILLAGER_CAMP = MODDED_STRUCTURES.register("pillager_camp", PillagerCampStructure::new);

    public static void register(IEventBus eventBus) {
        MODDED_STRUCTURES.register(eventBus);
    }

    public static void setupStructures() {
        //ModStructureUtils.setupMapSpacingAndLand(BASTILLE.get(), PillagerStructure.SEPARATION_SETTINGS, true);
        ModStructureUtils.setupMapSpacingAndLand(PILLAGER_CAMP.get(), PillagerStructure.SEPARATION_SETTINGS, true);
    }
}
