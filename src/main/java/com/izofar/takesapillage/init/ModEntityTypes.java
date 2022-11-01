package com.izofar.takesapillage.init;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.entity.Archer;
import com.izofar.takesapillage.entity.ClayGolem;
import com.izofar.takesapillage.entity.Legioner;
import com.izofar.takesapillage.entity.Skirmisher;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntityTypes {
    public static final EntityType<ClayGolem> CLAY_GOLEM = FabricEntityTypeBuilder.create(MobCategory.MISC, ClayGolem::new).dimensions(EntityDimensions.scalable(1.4F, 2.7F)).build();
    public static final EntityType<Archer> ARCHER = FabricEntityTypeBuilder.create(MobCategory.MONSTER, Archer::new).dimensions(EntityDimensions.scalable(0.6F, 1.95F)).spawnableFarFromPlayer().build();
    public static final EntityType<Skirmisher> SKIRMISHER = FabricEntityTypeBuilder.create(MobCategory.MONSTER, Skirmisher::new).dimensions(EntityDimensions.scalable(0.6F, 1.95F)).spawnableFarFromPlayer().build();
    public static final EntityType<Legioner> LEGIONER = FabricEntityTypeBuilder.create(MobCategory.MONSTER, Legioner::new).dimensions(EntityDimensions.scalable(0.6F, 1.95F)).spawnableFarFromPlayer().build();

    public static void registerEntities() {
        FabricDefaultAttributeRegistry.register(CLAY_GOLEM, ClayGolem.createAttributes());
        FabricDefaultAttributeRegistry.register(ARCHER, Archer.createAttributes());
        FabricDefaultAttributeRegistry.register(SKIRMISHER, Skirmisher.createAttributes());
        FabricDefaultAttributeRegistry.register(LEGIONER, Legioner.createAttributes());
        Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(TakesAPillageMod.MOD_ID, "clay_golem"), CLAY_GOLEM);
        Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(TakesAPillageMod.MOD_ID, "archer"), ARCHER);
        Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(TakesAPillageMod.MOD_ID, "skirmisher"), SKIRMISHER);
        Registry.register(Registry.ENTITY_TYPE, new ResourceLocation(TakesAPillageMod.MOD_ID, "legioner"), LEGIONER);
    }
}
