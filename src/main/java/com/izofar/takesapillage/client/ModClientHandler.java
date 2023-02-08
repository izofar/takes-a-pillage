package com.izofar.takesapillage.client;

import com.izofar.takesapillage.client.gui.ModConfigScreen;
import com.izofar.takesapillage.init.ModItems;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;

public class ModClientHandler {

    public static void registerConfigscreen(){
        ModLoadingContext.get().registerExtensionPoint(
                ExtensionPoint.CONFIGGUIFACTORY,
                () -> (mc, screen) -> new ModConfigScreen(screen)
        );
    }

    public static void registerItemModelProperties(){
        ItemModelsProperties.register(ModItems.RAVAGER_HORN.get(),
                new ResourceLocation("tooting"),
                (stack, clientWorld, livingEntity) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == stack ? 1.0F : 0.0F
        );
    }
}
