package com.izofar.takesapillage.init;

import com.izofar.takesapillage.TakesAPillageMod;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public abstract class ModConfiguredFeatures {

    public static final ConfiguredFeature<?, ?> CONFIGURED_MOB_FEATURE_ILLAGER = (ModFeatures.MOB_FEATURE_ILLAGER.get()).configured(NoneFeatureConfiguration.INSTANCE);

    public static final ConfiguredFeature<?, ?> CONFIGURED_MOB_FEATURE_RAVAGER = (ModFeatures.MOB_FEATURE_RAVAGER.get()).configured(NoneFeatureConfiguration.INSTANCE);

    public static final ConfiguredFeature<?, ?> CONFIGURED_MOB_FEATURE_LIVESTOCK = (ModFeatures.MOB_FEATURE_LIVESTOCK.get()).configured(NoneFeatureConfiguration.INSTANCE);

    public static final ConfiguredFeature<?, ?> CONFIGURED_MOB_FEATURE_PRISONER = (ModFeatures.MOB_FEATURE_PRISONER.get()).configured(NoneFeatureConfiguration.INSTANCE);

    public static final ConfiguredFeature<?, ?> CONFIGURED_MOB_FEATURE_ARCHER = (ModFeatures.MOB_FEATURE_ARCHER.get()).configured(NoneFeatureConfiguration.INSTANCE);

    public static void registerConfiguredFeatures() {
        Registry<ConfiguredFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_FEATURE;
        Registry.register(registry, new ResourceLocation(TakesAPillageMod.MODID, "mob_feature_illager"), CONFIGURED_MOB_FEATURE_ILLAGER);
        Registry.register(registry, new ResourceLocation(TakesAPillageMod.MODID, "mob_feature_ravager"), CONFIGURED_MOB_FEATURE_RAVAGER);
        Registry.register(registry, new ResourceLocation(TakesAPillageMod.MODID, "mob_feature_livestock"), CONFIGURED_MOB_FEATURE_LIVESTOCK);
        Registry.register(registry, new ResourceLocation(TakesAPillageMod.MODID, "mob_feature_prisoner"), CONFIGURED_MOB_FEATURE_PRISONER);
        Registry.register(registry, new ResourceLocation(TakesAPillageMod.MODID, "mob_feature_archer"), CONFIGURED_MOB_FEATURE_ARCHER);
    }
}
