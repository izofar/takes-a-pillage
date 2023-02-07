package com.izofar.takesapillage;

import com.izofar.takesapillage.client.gui.ModConfigScreen;
import com.izofar.takesapillage.config.ModCommonConfigs;
import com.izofar.takesapillage.event.ModBlockEvents;
import com.izofar.takesapillage.event.ModEntityEvents;
import com.izofar.takesapillage.event.ModWorldEvents;
import com.izofar.takesapillage.init.*;
import com.izofar.takesapillage.util.ModLists;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ExtensionPoint;
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
    public static final Logger LOGGER = LogManager.getLogger();

    public TakesAPillageMod() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModEntityTypes.register(eventBus);
        ModSoundEvents.register(eventBus);
        ModStructures.register(eventBus);
        ModFeatures.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModCommonConfigs.SPEC, "takesapillage-common.toml");
        ModLoadingContext.get().registerExtensionPoint(
                ExtensionPoint.CONFIGGUIFACTORY,
                () -> (mc, screen) -> new ModConfigScreen(screen)
        );

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(ModBlockEvents.class);
        MinecraftForge.EVENT_BUS.register(ModEntityEvents.class);
        MinecraftForge.EVENT_BUS.register(ModWorldEvents.class);
    }

    private void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModLists.resetVillagerHostiles();
            ModStructures.setupStructures();
            ModWorldEvents.addModdedRaiders();
            ModConfiguredFeatures.registerConfiguredFeatures();
            ModConfiguredStructures.registerConfiguredStructures();
        });
    }

    private void clientSetup(FMLClientSetupEvent event){
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ModItems.RAVAGER_HORN.get(),
                    new ResourceLocation("tooting"),
                    (stack, clientWorld, livingEntity) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == stack ? 1.0F : 0.0F
            );
        });
    }
}
