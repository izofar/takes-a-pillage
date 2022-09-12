package com.izofar.takesapillage.init;

import com.izofar.takesapillage.TakesAPillageMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class ModSoundEvents {
    public static final DeferredRegister<SoundEvent> MODDED_SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TakesAPillageMod.MODID);

    public static final RegistryObject<SoundEvent> CLAY_GOLEM_ATTACK = registerSoundEvent("entity.clay_golem.attack");
    public static final RegistryObject<SoundEvent> CLAY_GOLEM_DAMAGE = registerSoundEvent("entity.clay_golem.damage");
    public static final RegistryObject<SoundEvent> CLAY_GOLEM_DEATH = registerSoundEvent("entity.clay_golem.death");
    public static final RegistryObject<SoundEvent> CLAY_GOLEM_HURT = registerSoundEvent("entity.clay_golem.hurt");
    public static final RegistryObject<SoundEvent> CLAY_GOLEM_REPAIR = registerSoundEvent("entity.clay_golem.repair");
    public static final RegistryObject<SoundEvent> CLAY_GOLEM_STEP = registerSoundEvent("entity.clay_golem.step");

    public static final RegistryObject<SoundEvent> LEGIONER_AMBIENT = registerSoundEvent("entity.legioner.ambient");
    public static final RegistryObject<SoundEvent> LEGIONER_CELEBRATE = registerSoundEvent("entity.legioner.celebrate");
    public static final RegistryObject<SoundEvent> LEGIONER_DEATH = registerSoundEvent("entity.legioner.death");
    public static final RegistryObject<SoundEvent> LEGIONER_HURT = registerSoundEvent("entity.legioner.hurt");

    public static final RegistryObject<SoundEvent> BASTILLE_BLUES = registerSoundEvent("bastille_blues");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return MODDED_SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(TakesAPillageMod.MODID, name)));
    }

    public static void register(IEventBus eventBus) {
        MODDED_SOUND_EVENTS.register(eventBus);
    }
}
