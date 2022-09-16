package com.izofar.takesapillage.entity.ai;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.MathHelper;

public class ModIllagerAI {
    // Mob.doHurtTarget(Entity target) with modified DamageSource on line 28
    public static boolean doHurtTarget(AbstractIllagerEntity inflicter, Entity target, String damageSource){
        float f = (float)inflicter.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float f1 = (float)inflicter.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (target instanceof LivingEntity) {
            f += EnchantmentHelper.getDamageBonus(inflicter.getMainHandItem(), ((LivingEntity)target).getMobType());
            f1 += (float)EnchantmentHelper.getKnockbackBonus(inflicter);
        }

        int i = EnchantmentHelper.getFireAspect(inflicter);
        if (i > 0) {
            target.setSecondsOnFire(i * 4);
        }

        boolean flag = target.hurt(new EntityDamageSource(damageSource, inflicter), f);
        if (flag) {
            if (f1 > 0.0F && target instanceof LivingEntity) {
                ((LivingEntity)target).knockback(f1 * 0.5F, MathHelper.sin(inflicter.yRot * ((float)Math.PI / 180F)), -MathHelper.cos(inflicter.yRot * ((float)Math.PI / 180F)));
                inflicter.setDeltaMovement(inflicter.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            }

            if (target instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) target;
                inflicter.maybeDisableShield(player, inflicter.getMainHandItem(), player.isUsingItem() ? player.getUseItem() : ItemStack.EMPTY);
            }

            inflicter.doEnchantDamageEffects(inflicter, target);
            inflicter.setLastHurtMob(target);
        }

        return flag;
    }
}
