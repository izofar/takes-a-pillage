package com.izofar.takesapillage.entity.ai;

import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class ModIllagerAI {
    // Mob.doHurtTarget(Entity target) with modified DamageSource on line 28
    public static boolean doHurtTarget(AbstractIllager inflicter, Entity target, String damageSource){
        float f = (float)inflicter.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f1 = (float)inflicter.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (target instanceof LivingEntity) {
            f += EnchantmentHelper.getDamageBonus(inflicter.getMainHandItem(), ((LivingEntity)target).getMobType());
            f1 += (float) EnchantmentHelper.getKnockbackBonus(inflicter);
        }

        int i = EnchantmentHelper.getFireAspect(inflicter);
        if (i > 0) {
            target.setSecondsOnFire(i * 4);
        }

        boolean flag = target.hurt(new EntityDamageSource(damageSource, inflicter), f);
        if (flag) {
            if (f1 > 0.0F && target instanceof LivingEntity) {
                ((LivingEntity)target).knockback(f1 * 0.5F, Mth.sin(inflicter.getYRot() * ((float)Math.PI / 180F)), -Mth.cos(inflicter.getYRot() * ((float)Math.PI / 180F)));
                inflicter.setDeltaMovement(inflicter.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            }

            if (target instanceof Player player) {
                inflicter.maybeDisableShield(player, inflicter.getMainHandItem(), player.isUsingItem() ? player.getUseItem() : ItemStack.EMPTY);
            }

            inflicter.doEnchantDamageEffects(inflicter, target);
            inflicter.setLastHurtMob(target);
        }

        return flag;
    }
}
