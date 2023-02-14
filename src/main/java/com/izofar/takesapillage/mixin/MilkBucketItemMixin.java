package com.izofar.takesapillage.mixin;

import com.izofar.takesapillage.config.ModCommonConfigs;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;
import java.util.Map;

@Mixin(MilkBucketItem.class)
public class MilkBucketItemMixin {
    @Inject(method = "finishUsingItem", at = @At("HEAD"), cancellable = true)
    private void changeEffects(ItemStack stack, Level level, LivingEntity entity, CallbackInfoReturnable<ItemStack> cir) {
        if(!ModCommonConfigs.REMOVE_BAD_OMEN) {
            if (entity instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
                serverPlayer.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            }

            if (entity instanceof Player && !((Player)entity).getAbilities().instabuild) {
                stack.shrink(1);
            }

            if (!level.isClientSide) {
                Map<MobEffect, MobEffectInstance> map = entity.getActiveEffectsMap();

                for(Iterator<MobEffect> iterator = map.keySet().iterator(); iterator.hasNext();) {
                    MobEffect effect = iterator.next();
                    MobEffectInstance instance = map.get(effect);

                    if(instance.getEffect() != MobEffects.BAD_OMEN) {
                        iterator.remove();
                        entity.onEffectRemoved(instance);
                    }
                }
            }
            cir.setReturnValue(stack.isEmpty() ? new ItemStack(Items.BUCKET) : stack);
        }
    }
}
