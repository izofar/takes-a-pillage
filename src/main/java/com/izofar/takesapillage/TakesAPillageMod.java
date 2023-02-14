package com.izofar.takesapillage;

import com.izofar.takesapillage.config.ModCommonConfigs;
import com.izofar.takesapillage.event.ModBlockEvents;
import com.izofar.takesapillage.event.ModEntityEvents;
import com.izofar.takesapillage.event.ModWorldEvents;
import com.izofar.takesapillage.init.*;
import com.izofar.takesapillage.util.ModLists;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TakesAPillageMod implements ModInitializer {
	public static final String MOD_ID = "takesapillage";
	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void onInitialize() {
		MidnightConfig.init(MOD_ID, ModCommonConfigs.class);
		ModItems.registerItems();
		ModEntityTypes.registerEntities();
		ModSoundEvents.registerSounds();
		ModStructures.registerStructures();
		ModFeatures.registerFeatures();

		ModBlockEvents.checkSpawnClayGolemOnBlockPlace();
		ModEntityEvents.replaceNaturallySpawningIronGolemsWithClayGolems();
		ModEntityEvents.checkForUnSpawnedGolem();
		ModLists.setupEntityLists();
		ModWorldEvents.onSpecialSpawn();
	}
}
