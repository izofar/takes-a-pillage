package com.izofar.takesapillage.mixin;

import com.izofar.takesapillage.init.ModEntityTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.GolemLastSeenSensor;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(GolemLastSeenSensor.class)
public abstract class ClayGolemSensorMixin {

    private static void checkForNearbyClayGolem(LivingEntity livingEntity) {
        Optional<List<LivingEntity>> optional = livingEntity.getBrain().getMemory(MemoryModuleType.LIVING_ENTITIES);
        if (optional.isPresent()) {
            boolean flag = optional.get().stream().anyMatch((target) -> target.getType().equals(ModEntityTypes.CLAY_GOLEM.get()));
            if (flag) {
                GolemLastSeenSensor.golemDetected(livingEntity);
            }

        }
    }

    @Inject(
        method = "doTick(Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/entity/LivingEntity;)V",
        at = @At(value = "RETURN")
    )
    private void takesapillage_doTick(ServerWorld serverLevel, LivingEntity livingEntity, CallbackInfo ci) {
        checkForNearbyClayGolem(livingEntity);
    }
}
