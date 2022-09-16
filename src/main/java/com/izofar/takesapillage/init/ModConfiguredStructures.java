package com.izofar.takesapillage.init;

import com.izofar.takesapillage.TakesAPillageMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.VillageConfig;

public class ModConfiguredStructures {

    private static final VillageConfig BASTILLE_CONFIG = createConfig(null, "bastille/start_pool", 3);
    private static final VillageConfig PILLAGER_CAMP_CONFIG = createConfig(null, "pillager_camp/start_pool", 6);

    public static final StructureFeature<?, ?> CONFIGURED_BASTILLE = ModStructures.PILLAGER_STRUCTURE.get().configured(BASTILLE_CONFIG);
    public static final StructureFeature<?, ?> CONFIGURED_PILLAGER_CAMP = ModStructures.PILLAGER_STRUCTURE.get().configured(PILLAGER_CAMP_CONFIG);

    public static void registerConfiguredStructures(){
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;

        Registry.register(registry, new ResourceLocation(TakesAPillageMod.MODID, "configured_bastion"), CONFIGURED_BASTILLE);
        Registry.register(registry, new ResourceLocation(TakesAPillageMod.MODID, "configured_pillager_camp"), CONFIGURED_PILLAGER_CAMP);
    }

    private static VillageConfig createConfig(DynamicRegistries dynamicRegistryManager, String location, int size){
        return new VillageConfig(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(new ResourceLocation(TakesAPillageMod.MODID, location)), size);
    }

}
