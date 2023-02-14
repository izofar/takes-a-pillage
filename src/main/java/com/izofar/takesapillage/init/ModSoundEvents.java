package com.izofar.takesapillage.init;

import com.izofar.takesapillage.TakesAPillageMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {
    public static final SoundEvent CLAY_GOLEM_ATTACK = sound("entity.clay_golem.attack");
    public static final SoundEvent CLAY_GOLEM_DAMAGE = sound("entity.clay_golem.damage");
    public static final SoundEvent CLAY_GOLEM_DEATH = sound("entity.clay_golem.death");
    public static final SoundEvent CLAY_GOLEM_HURT = sound("entity.clay_golem.hurt");
    public static final SoundEvent CLAY_GOLEM_REPAIR = sound("entity.clay_golem.repair");
    public static final SoundEvent CLAY_GOLEM_STEP = sound("entity.clay_golem.step");

    public static final SoundEvent LEGIONER_AMBIENT = sound("entity.legioner.ambient");
    public static final SoundEvent LEGIONER_CELEBRATE = sound("entity.legioner.celebrate");
    public static final SoundEvent LEGIONER_DEATH = sound("entity.legioner.death");
    public static final SoundEvent LEGIONER_HURT = sound("entity.legioner.hurt");

    public static final SoundEvent BASTILLE_BLUES = sound("bastille_blues");

    public static void registerSounds() {
        Registry.register(Registry.SOUND_EVENT, id("entity.clay_golem.attack"), CLAY_GOLEM_ATTACK);
        Registry.register(Registry.SOUND_EVENT, id("entity.clay_golem.damage"), CLAY_GOLEM_DAMAGE);
        Registry.register(Registry.SOUND_EVENT, id("entity.clay_golem.death"), CLAY_GOLEM_DEATH);
        Registry.register(Registry.SOUND_EVENT, id("entity.clay_golem.hurt"), CLAY_GOLEM_HURT);
        Registry.register(Registry.SOUND_EVENT, id("entity.clay_golem.repair"), CLAY_GOLEM_REPAIR);
        Registry.register(Registry.SOUND_EVENT, id("entity.clay_golem.step"), CLAY_GOLEM_STEP);
        Registry.register(Registry.SOUND_EVENT, id("entity.legioner.ambient"), LEGIONER_AMBIENT);
        Registry.register(Registry.SOUND_EVENT, id("entity.legioner.celebrate"), LEGIONER_CELEBRATE);
        Registry.register(Registry.SOUND_EVENT, id("entity.legioner.death"), LEGIONER_DEATH);
        Registry.register(Registry.SOUND_EVENT, id("entity.legioner.hurt"), LEGIONER_HURT);
        Registry.register(Registry.SOUND_EVENT, id("bastille_blues"), BASTILLE_BLUES);
    }

    private static ResourceLocation id(String id) {
        return new ResourceLocation(TakesAPillageMod.MOD_ID, id);
    }

    private static SoundEvent sound(String sound) {
        return new SoundEvent(id(sound));
    }
}
