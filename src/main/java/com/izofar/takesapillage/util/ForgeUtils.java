package com.izofar.takesapillage.util;

import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;

public class ForgeUtils {
    public static boolean canDisableShield(ItemStack stack) {
        return stack.getItem() instanceof AxeItem;
    }
}
