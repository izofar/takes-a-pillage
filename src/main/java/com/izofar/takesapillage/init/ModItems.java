package com.izofar.takesapillage.init;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.item.RavagerHornItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class ModItems {
    public static final DeferredRegister<Item> MODDED_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TakesAPillageMod.MODID);

    public static final RegistryObject<Item> ARCHER_SPAWN_EGG = MODDED_ITEMS.register("archer_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.ARCHER, 2374712, 9529169, (new Item.Properties()).tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> SKIRMISHER_SPAWN_EGG = MODDED_ITEMS.register("skirmisher_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.SKIRMISHER, 2374712, 9529169, (new Item.Properties()).tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> LEGIONER_SPAWN_EGG = MODDED_ITEMS.register("legioner_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.LEGIONER, 2374712, 9529169, (new Item.Properties()).tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> RAVAGER_HORN = MODDED_ITEMS.register("ravager_horn", () -> new RavagerHornItem((new Item.Properties()).tab(CreativeModeTab.TAB_TOOLS).defaultDurability(1)));

    public static void register(IEventBus eventBus) {
        MODDED_ITEMS.register(eventBus);
    }
}
