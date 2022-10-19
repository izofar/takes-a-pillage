package com.izofar.takesapillage.mixin;

import com.izofar.takesapillage.entity.Archer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageSource.class)
public class ArcherArrowMixin {

    @Inject(
        method = "arrow(Lnet/minecraft/world/entity/projectile/AbstractArrow;Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/world/damagesource/DamageSource;",
        at = @At(value = "RETURN"),
        cancellable = true
    )
    private static void takesapillage_arrow(AbstractArrow arrow, Entity entity, CallbackInfoReturnable<DamageSource> cir){
        if(entity instanceof Archer)
            cir.setReturnValue((new IndirectEntityDamageSource("archer", arrow, entity)).setProjectile());
    }

}
