package com.izofar.takesapillage.mixin;

import com.izofar.takesapillage.entity.ArcherEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageSource.class)
public class ArcherArrowMixin {

    @Inject(
        method = "arrow(Lnet/minecraft/entity/projectile/AbstractArrowEntity;Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/DamageSource;",
        at = @At(value = "RETURN"),
        cancellable = true
    )
    private static void takesapillage_arrow(AbstractArrowEntity arrow, Entity entity, CallbackInfoReturnable<DamageSource> cir){
        if(entity instanceof ArcherEntity)
            cir.setReturnValue((new IndirectEntityDamageSource("archer", arrow, entity)).setProjectile());
    }

}
