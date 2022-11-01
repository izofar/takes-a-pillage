package com.izofar.takesapillage.item;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

public class RavagerHornItem extends Item {
    public RavagerHornItem(Properties properties) {
        super(properties);
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.HORSE_BREATHE;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if (entityLiving instanceof Player player) {
            if (world instanceof ServerLevel level) {
                MobEffectInstance mobeffectinstance = player.getEffect(MobEffects.BAD_OMEN);
                int i = 1;
                if (mobeffectinstance != null) {
                    i += mobeffectinstance.getAmplifier();
                    player.removeEffectNoUpdate(MobEffects.BAD_OMEN);
                } else {
                    i--;
                }
                i = Mth.clamp(i, 0, 4);
                if (!level.getGameRules().getBoolean(GameRules.RULE_DISABLE_RAIDS))
                    player.addEffect(new MobEffectInstance(MobEffects.BAD_OMEN, 120000, i, false, false, true));
                stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
            } else player.playSound(SoundEvents.RAID_HORN, 32.0F, 1.0F);
        }
        return stack;
    }
}
