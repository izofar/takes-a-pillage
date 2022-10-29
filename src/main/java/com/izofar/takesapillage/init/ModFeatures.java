package com.izofar.takesapillage.init;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.util.ModLists;
import com.izofar.takesapillage.world.feature.MobFeature;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ModFeatures {
    public static final Feature<NoneFeatureConfiguration> MOB_FEATURE_ILLAGER = new MobFeature<>(ModLists::get_pillager_camp_list);
    public static final Feature<NoneFeatureConfiguration> MOB_FEATURE_RAVAGER = new MobFeature<>(EntityType.RAVAGER);
    public static final Feature<NoneFeatureConfiguration> MOB_FEATURE_LIVESTOCK = new MobFeature<>(ModLists::get_livestock_list);
    public static final Feature<NoneFeatureConfiguration> MOB_FEATURE_PRISONER = new MobFeature<>(ModLists::get_prisoner_list);
    public static final Feature<NoneFeatureConfiguration> MOB_FEATURE_ARCHER = new MobFeature<>(ModLists::get_ranged_illager_list);
    public static final Feature<NoneFeatureConfiguration> MOB_FEATURE_SOLDIER = new MobFeature<>(ModLists::get_bastille_list);
    public static final Feature<NoneFeatureConfiguration> MOB_FEATURE_CAPTIVE = new MobFeature<>(ModLists::get_captive_list);

    public static void registerFeatures() {
        Registry.register(Registry.FEATURE, new ResourceLocation(TakesAPillageMod.MOD_ID, "mob_feature_illager"), MOB_FEATURE_ILLAGER);
        Registry.register(Registry.FEATURE, new ResourceLocation(TakesAPillageMod.MOD_ID, "mob_feature_ravager"), MOB_FEATURE_RAVAGER);
        Registry.register(Registry.FEATURE, new ResourceLocation(TakesAPillageMod.MOD_ID, "mob_feature_livestock"), MOB_FEATURE_LIVESTOCK);
        Registry.register(Registry.FEATURE, new ResourceLocation(TakesAPillageMod.MOD_ID, "mob_feature_prisoner"), MOB_FEATURE_PRISONER);
        Registry.register(Registry.FEATURE, new ResourceLocation(TakesAPillageMod.MOD_ID, "mob_feature_archer"), MOB_FEATURE_ARCHER);
        Registry.register(Registry.FEATURE, new ResourceLocation(TakesAPillageMod.MOD_ID, "mob_feature_soldier"), MOB_FEATURE_SOLDIER);
        Registry.register(Registry.FEATURE, new ResourceLocation(TakesAPillageMod.MOD_ID, "mob_feature_captive"), MOB_FEATURE_CAPTIVE);
    }
}
