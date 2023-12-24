package com.izofar.takesapillage.mixin;

import com.izofar.takesapillage.entity.Archer;
import com.izofar.takesapillage.entity.Legioner;
import com.izofar.takesapillage.entity.Skirmisher;
import com.izofar.takesapillage.init.ModDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageSources.class)
public class CustomDeathMessageMixin {

    @Inject(
            method = "mobAttack(Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/damagesource/DamageSource;",
            at = @At(value = "RETURN"),
            cancellable = true
    )
    private void takesapillage_mobAttack(LivingEntity entity, CallbackInfoReturnable<DamageSource> cir){
        if(entity instanceof Legioner) {
            cir.setReturnValue(((DamageSources)(Object) this).source(ModDamageTypes.LEGIONER_SLAY, entity));
        }
        else if(entity instanceof Skirmisher) {
            cir.setReturnValue(((DamageSources)(Object) this).source(ModDamageTypes.SKIRMISHER_HACK, entity));
        }

    }

    @Inject(
        method = "arrow(Lnet/minecraft/world/entity/projectile/AbstractArrow;Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/world/damagesource/DamageSource;",
        at = @At(value = "RETURN"),
        cancellable = true
    )
    private void takesapillage_arrow(AbstractArrow arrow, Entity entity, CallbackInfoReturnable<DamageSource> cir){
        if(entity instanceof Archer) {
            cir.setReturnValue(((DamageSources)(Object) this).source(ModDamageTypes.ARCHER_SNIPE, arrow, entity));
        }
    }

}
