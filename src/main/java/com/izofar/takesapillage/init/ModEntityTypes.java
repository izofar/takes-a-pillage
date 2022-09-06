package com.izofar.takesapillage.init;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.entity.Archer;
import com.izofar.takesapillage.entity.ClayGolem;
import com.izofar.takesapillage.entity.Legioner;
import com.izofar.takesapillage.entity.Skirmisher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> MOD_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, TakesAPillageMod.MODID);

    public static final RegistryObject<EntityType<ClayGolem>> CLAY_GOLEM = MOD_ENTITY_TYPES.register("clay_golem", () -> EntityType.Builder.of(ClayGolem::new, MobCategory.MISC).sized(1.4F, 2.7F).clientTrackingRange(10).build((new ResourceLocation(TakesAPillageMod.MODID, "clay_golem")).toString()));
    public static final RegistryObject<EntityType<Archer>> ARCHER = MOD_ENTITY_TYPES.register("archer", () -> EntityType.Builder.of(Archer::new, MobCategory.MONSTER).canSpawnFarFromPlayer().sized(0.6F, 1.95F).clientTrackingRange(8).build((new ResourceLocation(TakesAPillageMod.MODID, "archer")).toString()));
    public static final RegistryObject<EntityType<Skirmisher>> SKIRMISHER = MOD_ENTITY_TYPES.register("skirmisher", () -> EntityType.Builder.of(Skirmisher::new, MobCategory.MONSTER).canSpawnFarFromPlayer().sized(0.6F, 1.95F).clientTrackingRange(8).build((new ResourceLocation(TakesAPillageMod.MODID, "skirmisher")).toString()));
    public static final RegistryObject<EntityType<Legioner>> LEGIONER = MOD_ENTITY_TYPES.register("legioner", () -> EntityType.Builder.of(Legioner::new, MobCategory.MONSTER).canSpawnFarFromPlayer().sized(0.6F, 1.95F).clientTrackingRange(8).build((new ResourceLocation(TakesAPillageMod.MODID, "legioner")).toString()));

    public static void register(IEventBus eventBus) {
        MOD_ENTITY_TYPES.register(eventBus);
    }
}
