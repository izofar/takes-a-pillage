package com.izofar.takesapillage.mixin;

import com.izofar.takesapillage.util.IMobRememberSpawnReason;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class MobRememberSpawnReasonMixin implements IMobRememberSpawnReason {

    @Unique
    private SpawnReason takesapillage_mobSpawnType;

    @Override
    public SpawnReason getMobSpawnType(){
        return takesapillage_mobSpawnType;
    }

    @Inject(
        method = "finalizeSpawn(Lnet/minecraft/world/IServerWorld;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/ILivingEntityData;Lnet/minecraft/nbt/CompoundNBT;)Lnet/minecraft/entity/ILivingEntityData;",
        at = @At(value = "HEAD")
    )
    private void takesaillage_finalizeSpawn(IServerWorld serverLevelAccessor, DifficultyInstance difficulty, SpawnReason spawnType, ILivingEntityData spawnGroupData, CompoundNBT tag, CallbackInfoReturnable<ILivingEntityData> cir){
        this.takesapillage_mobSpawnType = spawnType;
    }
}
