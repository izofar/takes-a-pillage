package com.izofar.takesapillage.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class RavagerHornItem extends Item {
    public RavagerHornItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.HORSE_BREATHE;
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        player.startUsingItem(hand);
        return ActionResult.consume(player.getItemInHand(hand));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entityLiving) {
        if (entityLiving instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityLiving;
            if (world instanceof ServerWorld) {
                EffectInstance mobeffectinstance = player.getEffect(Effects.BAD_OMEN);
                int i = 1;
                if (mobeffectinstance != null) {
                    i += mobeffectinstance.getAmplifier();
                    player.removeEffectNoUpdate(Effects.BAD_OMEN);
                } else {
                    i--;
                }
                i = MathHelper.clamp(i, 0, 4);
                if (!world.getGameRules().getBoolean(GameRules.RULE_DISABLE_RAIDS))
                    player.addEffect(new EffectInstance(Effects.BAD_OMEN, 120000, i, false, false, true));
                stack.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
            } else player.playSound(SoundEvents.RAID_HORN, 32.0F, 1.0F);
        }
        return stack;
    }
}
