package com.izofar.takesapillage.item;

import com.izofar.takesapillage.init.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

public class RavagerHornItem extends InstrumentItem {

    private TagKey<Instrument> instruments;

    public RavagerHornItem(Item.Properties properties, TagKey<Instrument> instruments) {
        super(properties, instruments);
        this.instruments = instruments;
    }

    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> stackList) {
        if (this.allowedIn(tab)) {
            for(Holder<Instrument> holder : Registry.INSTRUMENT.getTagOrEmpty(this.instruments)) {
                stackList.add(create(ModItems.RAVAGER_HORN.get(), holder));
            }
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int remainingTicks) {
        super.finishUsingItem(stack, level, livingEntity);
        livingEntity.getItemInHand(livingEntity.getUsedItemHand()).hurtAndBreak(1, livingEntity, p -> p.broadcastBreakEvent(p.getUsedItemHand()));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        InteractionResultHolder<ItemStack> result = super.use(world, player, hand);
        if (world instanceof ServerLevel level) {
            incrementBadOmen(level, player);
        }
        return result;
    }

    private static void incrementBadOmen(ServerLevel level, Player player){
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
    }
}
