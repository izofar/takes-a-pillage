package com.izofar.takesapillage.init;

import com.izofar.takesapillage.TakesAPillageMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.StructureFeature;

public abstract class ModConfiguredStructures {

    // null is safe here because we toss it out in PillagerStructure$Start
    public static final StructureFeature<?, ?> CONFIGURED_BASTILLE = ModStructures.BASTILLE.get().configured(null);
    public static final StructureFeature<?, ?> CONFIGURED_PILLAGER_CAMP = ModStructures.PILLAGER_CAMP.get().configured(null);


    public static void registerConfiguredStructures(){
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;

        Registry.register(registry, new ResourceLocation(TakesAPillageMod.MODID, "configured_bastille"), CONFIGURED_BASTILLE);
        Registry.register(registry, new ResourceLocation(TakesAPillageMod.MODID, "configured_pillager_camp"), CONFIGURED_PILLAGER_CAMP);

        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.BASTILLE.get(), CONFIGURED_BASTILLE);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.PILLAGER_CAMP.get(), CONFIGURED_PILLAGER_CAMP);
    }

}
