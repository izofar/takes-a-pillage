package com.izofar.takesapillage;

import com.izofar.takesapillage.client.gui.ModConfigScreen;
import com.izofar.takesapillage.config.ModCommonConfigs;
import com.izofar.takesapillage.event.ModWorldEvents;
import com.izofar.takesapillage.init.*;
import com.izofar.takesapillage.util.ModLists;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TakesAPillageMod.MODID)
public class TakesAPillageMod
{
    public static final String MODID = "takesapillage";
    public static final String NAME = "It Takes a Pillage";
    public static final Logger LOGGER = LogManager.getLogger();

    public TakesAPillageMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModInstruments.register(eventBus);
        ModItems.register(eventBus);
        ModEntityTypes.register(eventBus);
        ModSoundEvents.register(eventBus);
        ModStructures.register(eventBus);
        ModFeatures.register(eventBus);

        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::clientSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModCommonConfigs.SPEC, "takesapillage-common.toml");
        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> new ModConfigScreen(parent))
        );

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModLists.setupEntityLists();
            ModWorldEvents.addModdedRaiders();
        });
    }

    private void clientSetup(FMLClientSetupEvent event){
        event.enqueueWork(() -> {
            ItemProperties.register(ModItems.RAVAGER_HORN.get(),
                    new ResourceLocation("tooting"),
                    (stack, level, livingEntity, unusedInt) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == stack ? 1.0F : 0.0F
                );
        });
    }
}
