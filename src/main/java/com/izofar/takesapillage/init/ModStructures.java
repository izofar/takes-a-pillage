package com.izofar.takesapillage.init;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.world.structure.PillagerStructure;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class ModStructures {
    public static StructureType<?> PILLAGER_STRUCTURE = register("pillager_structure", PillagerStructure.CODEC);

    public static void init() {}

    private static <S extends Structure> StructureType<S> register(String id, Codec<S> codec) {
        return Registry.register(Registry.STRUCTURE_TYPES, new ResourceLocation(TakesAPillageMod.MOD_ID, id), () -> codec);
    }
}
