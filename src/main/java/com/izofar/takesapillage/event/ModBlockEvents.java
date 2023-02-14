package com.izofar.takesapillage.event;

import com.izofar.takesapillage.entity.ClayGolem;

public class ModBlockEvents {
    public static void checkSpawnClayGolemOnBlockPlace() {
        BlockPlaceEvents.EVENT.register((level, pos, state, placer, stack) -> {
            if(ClayGolem.PUMPKINS_PREDICATE.test(state)) {
                ClayGolem.trySpawnClayGolem(level, pos);
            }
        });
    }
}
