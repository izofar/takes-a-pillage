package com.izofar.takesapillage.init;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.entity.ArcherEntity;
import com.izofar.takesapillage.entity.ClayGolemEntity;
import com.izofar.takesapillage.entity.LegionerEntity;
import com.izofar.takesapillage.entity.SkirmisherEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> MOD_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, TakesAPillageMod.MODID);

    public static final RegistryObject<EntityType<ClayGolemEntity>> CLAY_GOLEM = MOD_ENTITY_TYPES.register("clay_golem", () -> EntityType.Builder.of(ClayGolemEntity::new, EntityClassification.MISC).sized(1.4F, 2.7F).clientTrackingRange(10).build((new ResourceLocation(TakesAPillageMod.MODID, "clay_golem")).toString()));
    public static final RegistryObject<EntityType<ArcherEntity>> ARCHER = MOD_ENTITY_TYPES.register("archer", () -> EntityType.Builder.of(ArcherEntity::new, EntityClassification.MONSTER).canSpawnFarFromPlayer().sized(0.6F, 1.95F).clientTrackingRange(8).build((new ResourceLocation(TakesAPillageMod.MODID, "archer")).toString()));
    public static final RegistryObject<EntityType<SkirmisherEntity>> SKIRMISHER = MOD_ENTITY_TYPES.register("skirmisher", () -> EntityType.Builder.of(SkirmisherEntity::new, EntityClassification.MONSTER).canSpawnFarFromPlayer().sized(0.6F, 1.95F).clientTrackingRange(8).build((new ResourceLocation(TakesAPillageMod.MODID, "skirmisher")).toString()));
    public static final RegistryObject<EntityType<LegionerEntity>> LEGIONER = MOD_ENTITY_TYPES.register("legioner", () -> EntityType.Builder.of(LegionerEntity::new, EntityClassification.MONSTER).canSpawnFarFromPlayer().sized(0.6F, 1.95F).clientTrackingRange(8).build((new ResourceLocation(TakesAPillageMod.MODID, "legioner")).toString()));

    public static void register(IEventBus eventBus) {
        MOD_ENTITY_TYPES.register(eventBus);
    }
}
