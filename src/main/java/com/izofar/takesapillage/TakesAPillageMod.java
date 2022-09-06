package com.izofar.takesapillage;

import com.izofar.takesapillage.init.*;
import com.izofar.takesapillage.util.ModLists;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TakesAPillageMod.MODID)
public class TakesAPillageMod
{
    public static final String MODID = "takesapillage";
    public static final Logger LOGGER = LogManager.getLogger();

    public TakesAPillageMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(eventBus);
        ModEntityTypes.register(eventBus);
        ModSoundEvents.register(eventBus);
        ModStructures.register(eventBus);
        ModFeatures.register(eventBus);
        eventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModLists.setupEntityLists();
        });
    }
}
