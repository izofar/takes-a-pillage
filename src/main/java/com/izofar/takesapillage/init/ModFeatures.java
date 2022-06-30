package com.izofar.takesapillage.init;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.util.ModLists;
import com.izofar.takesapillage.world.feature.MobFeature;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class ModFeatures {
    public static final DeferredRegister<Feature<?>> MODDED_FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, TakesAPillageMod.MODID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> MOB_FEATURE_ILLAGER = MODDED_FEATURES.register("mob_feature_illager", () -> new MobFeature(ModLists::get_pillager_camp_list));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> MOB_FEATURE_RAVAGER = MODDED_FEATURES.register("mob_feature_ravager", () -> new MobFeature(EntityType.RAVAGER));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> MOB_FEATURE_LIVESTOCK = MODDED_FEATURES.register("mob_feature_livestock", () -> new MobFeature(ModLists::get_livestock_list));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> MOB_FEATURE_PRISONER = MODDED_FEATURES.register("mob_feature_prisoner", () -> new MobFeature(ModLists::get_prisoner_list));

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> MOB_FEATURE_ARCHER = MODDED_FEATURES.register("mob_feature_archer", () -> new MobFeature(ModLists::get_ranged_illager_list));

    public static void register(IEventBus eventBus) {
        MODDED_FEATURES.register(eventBus);
    }
}
