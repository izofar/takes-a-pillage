package com.izofar.takesapillage.init;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.world.configuration.ModJigsawConfiguration;
import com.izofar.takesapillage.world.structure.PillagerStructure;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;

public class ModStructures {
    public static final StructureFeature<JigsawConfiguration> PILLAGER_STRUCTURE = new PillagerStructure(ModJigsawConfiguration.CODEC);

    public static void registerStructures() {
        Registry.register(Registry.STRUCTURE_FEATURE, new ResourceLocation(TakesAPillageMod.MOD_ID, "pillager_structure"), PILLAGER_STRUCTURE);
    }
}
