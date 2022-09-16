package com.izofar.takesapillage.entity;

import com.izofar.takesapillage.init.ModEntityTypes;
import com.izofar.takesapillage.init.ModSoundEvents;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.pattern.BlockMaterialMatcher;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class ClayGolemEntity extends IronGolemEntity {

    private static BlockPattern clayGolemFull;

    public static final Predicate<BlockState> PUMPKINS_PREDICATE = (blockState -> (blockState != null && (blockState.is(Blocks.CARVED_PUMPKIN) || blockState.is(Blocks.JACK_O_LANTERN))));

    public ClayGolemEntity(EntityType<? extends IronGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D);
    }

    public static BlockPattern getOrCreateClayGolemFull() {
        if (clayGolemFull == null)
            clayGolemFull = BlockPatternBuilder.start().aisle("~^~", "###", "~#~")
                    .where('^', CachedBlockInfo.hasState(PUMPKINS_PREDICATE))
                    .where('#', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.CLAY)))
                    .where('~', CachedBlockInfo.hasState(BlockMaterialMatcher.forMaterial(Material.AIR)))
                .build();
        return clayGolemFull;
    }

    public static void trySpawnClayGolem(World world, BlockPos blockPos) {
        BlockPattern golemPattern = getOrCreateClayGolemFull();
        BlockPattern.PatternHelper blockpattern$patternhelper = golemPattern.find(world, blockPos);
        if (blockpattern$patternhelper == null) return;
        for (int j = 0; j < golemPattern.getWidth(); j++) {
            for (int k = 0; k < golemPattern.getHeight(); k++) {
                CachedBlockInfo cachedblockinfo = blockpattern$patternhelper.getBlock(j, k, 0);
                world.setBlock(cachedblockinfo.getPos(), Blocks.AIR.defaultBlockState(), 2);
                world.levelEvent(2001, cachedblockinfo.getPos(), Block.getId(cachedblockinfo.getState()));
            }
        }
        BlockPos blockpos = blockpattern$patternhelper.getBlock(1, 2, 0).getPos();
        ClayGolemEntity claygolem = ModEntityTypes.CLAY_GOLEM.get().create(world);
        if(claygolem == null) return;
        claygolem.setPlayerCreated(true);
        claygolem.moveTo(blockpos.getX() + 0.5D, blockpos.getY() + 0.05D, blockpos.getZ() + 0.5D, 0.0F, 0.0F);
        world.addFreshEntity(claygolem);
        for (ServerPlayerEntity serverplayerentity1 : world.getEntitiesOfClass(ServerPlayerEntity.class, claygolem.getBoundingBox().inflate(5.0D)))
            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayerentity1, claygolem);
        for (int i = 0; i < golemPattern.getWidth(); i++) {
            for (int k = 0; k < golemPattern.getHeight(); k++)
                world.blockUpdated(blockpattern$patternhelper.getBlock(i, k, 0).getPos(), Blocks.AIR);
        }
    }

    // IronGolem.goHurtTarget(Entity target) with modified DamageSource on line 87
    @Override
    public boolean doHurtTarget(Entity target){
        this.attackAnimationTick = 10;
        this.level.broadcastEntityEvent(this, (byte)4);
        float f = this.getAttackDamage();
        float f1 = (int)f > 0 ? f / 2.0F + (float)this.random.nextInt((int)f) : f;
        boolean flag = target.hurt(new EntityDamageSource("clay_golem", this), f1);
        if (flag) {
            target.setDeltaMovement(target.getDeltaMovement().add(0.0D, 0.4F, 0.0D));
            this.doEnchantDamageEffects(this, target);
        }

        this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.0F);
        return flag;
    }

    protected ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.getItem() != Items.CLAY_BALL) {
            return ActionResultType.PASS;
        } else {
            float f = this.getHealth();
            this.heal(10.0F);
            if (this.getHealth() == f) {
                return ActionResultType.PASS;
            } else {
                float f1 = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                this.playSound(ModSoundEvents.CLAY_GOLEM_REPAIR.get(), 1.0F, f1);
                if (!player.abilities.instabuild) {
                    itemstack.shrink(1);
                }

                return ActionResultType.sidedSuccess(this.level.isClientSide);
            }
        }
    }

    @Override
    public void playSound(SoundEvent soundEvent, float volume, float pitch) {
        if (soundEvent == SoundEvents.IRON_GOLEM_ATTACK) {
            super.playSound(ModSoundEvents.CLAY_GOLEM_ATTACK.get(), volume, pitch);
        } else if (soundEvent == SoundEvents.IRON_GOLEM_DAMAGE) {
            super.playSound(ModSoundEvents.CLAY_GOLEM_DAMAGE.get(), volume, pitch);
        } else if (soundEvent == SoundEvents.IRON_GOLEM_DEATH) {
            super.playSound(ModSoundEvents.CLAY_GOLEM_DEATH.get(), volume, pitch);
        } else if (soundEvent == SoundEvents.IRON_GOLEM_HURT) {
            super.playSound(ModSoundEvents.CLAY_GOLEM_HURT.get(), volume, pitch);
        } else if (soundEvent == SoundEvents.IRON_GOLEM_REPAIR) {
            super.playSound(ModSoundEvents.CLAY_GOLEM_REPAIR.get(), volume, pitch);
        } else if (soundEvent == SoundEvents.IRON_GOLEM_STEP) {
            super.playSound(ModSoundEvents.CLAY_GOLEM_STEP.get(), volume, pitch);
        } else {
            super.playSound(soundEvent, volume, pitch);
        }
    }
}
