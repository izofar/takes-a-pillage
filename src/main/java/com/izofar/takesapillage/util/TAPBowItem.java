package com.izofar.takesapillage.util;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;

public class TAPBowItem extends BowItem {
    public TAPBowItem(Properties properties) {
        super(properties);
    }

    public static AbstractArrow customArrow(AbstractArrow arrow) {
        return arrow;
    }
}
