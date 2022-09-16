package com.izofar.takesapillage.init;

import com.izofar.takesapillage.TakesAPillageMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ModConfiguredFeatures {

    public static ConfiguredFeature<?, ?> CONFIGURED_MOB_FEATURE_ILLAGER = ModFeatures.MOB_FEATURE_ILLAGER.get().configured(IFeatureConfig.NONE);
    public static ConfiguredFeature<?, ?> CONFIGURED_MOB_FEATURE_RAVAGER = ModFeatures.MOB_FEATURE_RAVAGER.get().configured(IFeatureConfig.NONE);
    public static ConfiguredFeature<?, ?> CONFIGURED_MOB_FEATURE_LIVESTOCK = ModFeatures.MOB_FEATURE_LIVESTOCK.get().configured(IFeatureConfig.NONE);
    public static ConfiguredFeature<?, ?> CONFIGURED_MOB_FEATURE_PRISONER = ModFeatures.MOB_FEATURE_PRISONER.get().configured(IFeatureConfig.NONE);
    public static ConfiguredFeature<?, ?> CONFIGURED_MOB_FEATURE_ARCHER = ModFeatures.MOB_FEATURE_ARCHER.get().configured(IFeatureConfig.NONE);
    public static ConfiguredFeature<?, ?> CONFIGURED_MOB_FEATURE_SOLDIER = ModFeatures.MOB_FEATURE_SOLDIER.get().configured(IFeatureConfig.NONE);
    public static ConfiguredFeature<?, ?> CONFIGURED_MOB_FEATURE_CAPTIVE = ModFeatures.MOB_FEATURE_CAPTIVE.get().configured(IFeatureConfig.NONE);

    public static void registerConfiguredFeatures(){
        Registry<ConfiguredFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_FEATURE;

        Registry.register(registry, new ResourceLocation(TakesAPillageMod.MODID, "mob_feature_illager"), CONFIGURED_MOB_FEATURE_ILLAGER);
        Registry.register(registry, new ResourceLocation(TakesAPillageMod.MODID, "mob_feature_ravager"), CONFIGURED_MOB_FEATURE_RAVAGER);
        Registry.register(registry, new ResourceLocation(TakesAPillageMod.MODID, "mob_feature_livestock"), CONFIGURED_MOB_FEATURE_LIVESTOCK);
        Registry.register(registry, new ResourceLocation(TakesAPillageMod.MODID, "mob_feature_prisoner"), CONFIGURED_MOB_FEATURE_PRISONER);
        Registry.register(registry, new ResourceLocation(TakesAPillageMod.MODID, "mob_feature_archer"), CONFIGURED_MOB_FEATURE_ARCHER);
        Registry.register(registry, new ResourceLocation(TakesAPillageMod.MODID, "mob_feature_soldier"), CONFIGURED_MOB_FEATURE_SOLDIER);
        Registry.register(registry, new ResourceLocation(TakesAPillageMod.MODID, "mob_feature_captive"), CONFIGURED_MOB_FEATURE_CAPTIVE);
    }

}
