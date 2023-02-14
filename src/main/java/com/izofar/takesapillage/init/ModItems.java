package com.izofar.takesapillage.init;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.item.CustomRecordItem;
import com.izofar.takesapillage.item.RavagerHornItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

public class ModItems {
    public static final Item ARCHER_SPAWN_EGG = new SpawnEggItem(ModEntityTypes.ARCHER, 0x243c38, 0x916751, new FabricItemSettings().group(CreativeModeTab.TAB_MISC));
    public static final Item SKIRMISHER_SPAWN_EGG = new SpawnEggItem(ModEntityTypes.SKIRMISHER, 0x421b1e, 0x916751, new FabricItemSettings().group(CreativeModeTab.TAB_MISC));
    public static final Item LEGIONER_SPAWN_EGG = new SpawnEggItem(ModEntityTypes.LEGIONER, 0x2b1a33, 0x916751, new FabricItemSettings().group(CreativeModeTab.TAB_MISC));

    public static final Item RAVAGER_HORN = new RavagerHornItem(new FabricItemSettings().group(CreativeModeTab.TAB_TOOLS).maxDamage(1));
    public static final Item BASTILLE_BLUES = new CustomRecordItem(4, ModSoundEvents.BASTILLE_BLUES, new FabricItemSettings().group(CreativeModeTab.TAB_MISC).stacksTo(1).rarity(Rarity.RARE));

    public static void registerItems() {
        Registry.register(Registry.ITEM, new ResourceLocation(TakesAPillageMod.MOD_ID, "archer_spawn_egg"), ARCHER_SPAWN_EGG);
        Registry.register(Registry.ITEM, new ResourceLocation(TakesAPillageMod.MOD_ID, "skirmisher_spawn_egg"), SKIRMISHER_SPAWN_EGG);
        Registry.register(Registry.ITEM, new ResourceLocation(TakesAPillageMod.MOD_ID, "legioner_spawn_egg"), LEGIONER_SPAWN_EGG);
        Registry.register(Registry.ITEM, new ResourceLocation(TakesAPillageMod.MOD_ID, "ravager_horn"), RAVAGER_HORN);
        Registry.register(Registry.ITEM, new ResourceLocation(TakesAPillageMod.MOD_ID, "bastille_blues_music_disc"), BASTILLE_BLUES);
    }
}
