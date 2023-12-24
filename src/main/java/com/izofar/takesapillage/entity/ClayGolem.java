package com.izofar.takesapillage.entity;

import com.izofar.takesapillage.init.ModEntityTypes;
import com.izofar.takesapillage.init.ModSoundEvents;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;

import java.util.function.Predicate;

public class ClayGolem extends IronGolem {

    private static BlockPattern clayGolemFull;

    public static final Predicate<BlockState> PUMPKINS_PREDICATE = (blockState -> (blockState != null && (blockState.is(Blocks.CARVED_PUMPKIN) || blockState.is(Blocks.JACK_O_LANTERN))));

    public ClayGolem(EntityType<? extends IronGolem> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .add(Attributes.ATTACK_DAMAGE, 8.0D);
    }

    public static BlockPattern getOrCreateClayGolemFull() {
        if (clayGolemFull == null)
            clayGolemFull = BlockPatternBuilder.start().aisle("~^~", "###", "~#~").where('^', BlockInWorld.hasState(PUMPKINS_PREDICATE)).where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.CLAY))).where('~', (block) -> block.getState().isAir()).build();
        return clayGolemFull;
    }

    public static void trySpawnClayGolem(Level world, BlockPos blockPos) {
        BlockPattern golemPattern = getOrCreateClayGolemFull();
        BlockPattern.BlockPatternMatch blockpattern$patternhelper = golemPattern.find(world, blockPos);
        if (blockpattern$patternhelper == null) return;
        for (int j = 0; j < golemPattern.getWidth(); j++) {
            for (int k = 0; k < golemPattern.getHeight(); k++) {
                BlockInWorld cachedblockinfo = blockpattern$patternhelper.getBlock(j, k, 0);
                world.setBlock(cachedblockinfo.getPos(), Blocks.AIR.defaultBlockState(), 2);
                world.levelEvent(2001, cachedblockinfo.getPos(), Block.getId(cachedblockinfo.getState()));
            }
        }
        BlockPos blockpos = blockpattern$patternhelper.getBlock(1, 2, 0).getPos();
        ClayGolem claygolem = ModEntityTypes.CLAY_GOLEM.get().create(world);
        if(claygolem == null) return;
        claygolem.setPlayerCreated(true);
        claygolem.moveTo(blockpos.getX() + 0.5D, blockpos.getY() + 0.05D, blockpos.getZ() + 0.5D, 0.0F, 0.0F);
        world.addFreshEntity(claygolem);
        for (ServerPlayer serverplayerentity1 : world.getEntitiesOfClass(ServerPlayer.class, claygolem.getBoundingBox().inflate(5.0D)))
            CriteriaTriggers.SUMMONED_ENTITY.trigger(serverplayerentity1, claygolem);
        for (int i = 0; i < golemPattern.getWidth(); i++) {
            for (int k = 0; k < golemPattern.getHeight(); k++)
                world.blockUpdated(blockpattern$patternhelper.getBlock(i, k, 0).getPos(), Blocks.AIR);
        }
    }

    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (!itemstack.is(Items.CLAY_BALL)) {
            return InteractionResult.PASS;
        } else {
            float f = this.getHealth();
            this.heal(10.0F);
            if (this.getHealth() == f) {
                return InteractionResult.PASS;
            } else {
                float f1 = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                this.playSound(ModSoundEvents.CLAY_GOLEM_REPAIR.get(), 1.0F, f1);
                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }

                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }
    }

    @Override
    public void playSound(SoundEvent soundEvent, float f1, float f2) {
        if (soundEvent == SoundEvents.IRON_GOLEM_ATTACK) {
            super.playSound(ModSoundEvents.CLAY_GOLEM_ATTACK.get(), f1, f2);
        } else if (soundEvent == SoundEvents.IRON_GOLEM_DAMAGE) {
            super.playSound(ModSoundEvents.CLAY_GOLEM_DAMAGE.get(), f1, f2);
        } else if (soundEvent == SoundEvents.IRON_GOLEM_DEATH) {
            super.playSound(ModSoundEvents.CLAY_GOLEM_DEATH.get(), f1, f2);
        } else if (soundEvent == SoundEvents.IRON_GOLEM_HURT) {
            super.playSound(ModSoundEvents.CLAY_GOLEM_HURT.get(), f1, f2);
        } else if (soundEvent == SoundEvents.IRON_GOLEM_REPAIR) {
            super.playSound(ModSoundEvents.CLAY_GOLEM_REPAIR.get(), f1, f2);
        } else if (soundEvent == SoundEvents.IRON_GOLEM_STEP) {
            super.playSound(ModSoundEvents.CLAY_GOLEM_STEP.get(), f1, f2);
        } else {
            super.playSound(soundEvent, f1, f2);
        }
    }
}
