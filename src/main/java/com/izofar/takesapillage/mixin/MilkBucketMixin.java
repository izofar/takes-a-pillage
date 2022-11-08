package com.izofar.takesapillage.mixin;

import com.izofar.takesapillage.config.ModCommonConfigs;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.MilkBucketItem;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Iterator;
import java.util.Map;

@Mixin(value = MilkBucketItem.class, priority = 1333)
public class MilkBucketMixin {
    @Redirect(method = "finishUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;removeAllEffects()Z"))
    public boolean changeEffects(@NotNull LivingEntity entity) {
        Map<MobEffect, MobEffectInstance> map = entity.getActiveEffectsMap();
        boolean changed = false;

        for(Iterator<MobEffect> iterator = map.keySet().iterator(); iterator.hasNext();) {
            MobEffect effect = iterator.next();
            MobEffectInstance instance = map.get(effect);

            if(ModCommonConfigs.REMOVE_BAD_OMEN || instance.getEffect() != MobEffects.BAD_OMEN) {
                iterator.remove();
                entity.onEffectRemoved(instance);
                changed = true;
            }
        }
        return changed;
    }
}
