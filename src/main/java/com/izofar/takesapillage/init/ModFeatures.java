package com.izofar.takesapillage.init;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.util.ModLists;
import com.izofar.takesapillage.world.feature.MobFeature;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class ModFeatures {
    public static final DeferredRegister<Feature<?>> MODDED_FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, TakesAPillageMod.MODID);

    public static final RegistryObject<Feature<NoFeatureConfig>> MOB_FEATURE_ILLAGER = MODDED_FEATURES.register("mob_feature_illager", () -> new MobFeature<>(ModLists.getPillagerCampList()));
    public static final RegistryObject<Feature<NoFeatureConfig>> MOB_FEATURE_RAVAGER = MODDED_FEATURES.register("mob_feature_ravager", () -> new MobFeature<>(() -> EntityType.RAVAGER));
    public static final RegistryObject<Feature<NoFeatureConfig>> MOB_FEATURE_LIVESTOCK = MODDED_FEATURES.register("mob_feature_livestock", () -> new MobFeature<>(ModLists.getLivestockList()));
    public static final RegistryObject<Feature<NoFeatureConfig>> MOB_FEATURE_PRISONER = MODDED_FEATURES.register("mob_feature_prisoner", () -> new MobFeature<>(ModLists.getPrisonerList()));
    public static final RegistryObject<Feature<NoFeatureConfig>> MOB_FEATURE_ARCHER = MODDED_FEATURES.register("mob_feature_archer", () -> new MobFeature<>(ModLists.getRangedIllagerList()));
    public static final RegistryObject<Feature<NoFeatureConfig>> MOB_FEATURE_SOLDIER = MODDED_FEATURES.register("mob_feature_soldier", () -> new MobFeature<>(ModLists.getBastilleList()));
    public static final RegistryObject<Feature<NoFeatureConfig>> MOB_FEATURE_CAPTIVE = MODDED_FEATURES.register("mob_feature_captive", () -> new MobFeature<>(ModLists.getCaptiveList()));


    public static void register(IEventBus eventBus) {
        MODDED_FEATURES.register(eventBus);
    }
}
