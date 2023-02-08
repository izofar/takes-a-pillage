package com.izofar.takesapillage.client;

import com.izofar.takesapillage.client.gui.ModConfigScreen;
import com.izofar.takesapillage.init.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;

public class ModClientHandler {

    public static void registerConfigScreen(){
        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> new ModConfigScreen(parent))
        );
    }

    public static void registerItemModelProperties(){
        ItemProperties.register(ModItems.RAVAGER_HORN.get(),
                new ResourceLocation("tooting"),
                (stack, level, livingEntity, unusedInt) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == stack ? 1.0F : 0.0F
        );
    }
}
