package com.izofar.takesapillage.integration;

import com.izofar.takesapillage.TakesAPillageMod;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import eu.midnightdust.lib.config.MidnightConfig;

public class ModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return screen -> MidnightConfig.getScreen(screen, TakesAPillageMod.MOD_ID);
    }
}
