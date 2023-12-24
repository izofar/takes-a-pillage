package com.izofar.takesapillage.init;

import com.izofar.takesapillage.TakesAPillageMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Instrument;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public abstract class ModInstruments {

    public static final DeferredRegister<Instrument> MODDED_INSTRUMENTS = DeferredRegister.create(Registries.INSTRUMENT, TakesAPillageMod.MODID);

    public static final RegistryObject<Instrument> RAID_HORN = MODDED_INSTRUMENTS.register("raid_horn", () -> new Instrument(SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(2), 140, 256.0F));

    public static final TagKey<Instrument> RAVAGER_HORNS = createTag("ravager_horns");

    public static void register(IEventBus eventBus) {
        MODDED_INSTRUMENTS.register(eventBus);
    }

    private static TagKey<Instrument> createTag(String location){
        return TagKey.create(Registries.INSTRUMENT, new ResourceLocation(TakesAPillageMod.MODID, location));
    }
}
