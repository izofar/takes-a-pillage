package com.izofar.takesapillage.entity;

import com.izofar.takesapillage.entity.ai.ModIllagerAI;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.GroundPathHelper;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;

public class SkirmisherEntity extends AbstractIllagerEntity {
    
    private static final Predicate<Difficulty> DOOR_BREAKING_PREDICATE = (difficulty -> (difficulty == Difficulty.NORMAL || difficulty == Difficulty.HARD));

    public SkirmisherEntity(EntityType<? extends AbstractIllagerEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void applyRaidBuffs(int round, boolean b) {}
    
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MonsterEntity.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.36D)
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new SkirmisherBreakDoorGoal(this));
        this.goalSelector.addGoal(2, new AbstractIllagerEntity.RaidOpenDoorGoal(this));
        this.goalSelector.addGoal(3, new AbstractRaiderEntity.FindTargetGoal(this, 10.0F));
        this.goalSelector.addGoal(4, new SkirmisherMeleeAttackGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, AbstractRaiderEntity.class)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
        this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
    }

    @Override
    public boolean doHurtTarget(Entity target){
        return ModIllagerAI.doHurtTarget(this, target, "skirmisher");
    }

    @Override
    public AbstractIllagerEntity.ArmPose getArmPose() {
        if (this.isAggressive()) return AbstractIllagerEntity.ArmPose.ATTACKING;
        return this.isCelebrating() ? AbstractIllagerEntity.ArmPose.CELEBRATING : AbstractIllagerEntity.ArmPose.CROSSED;
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) { this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_AXE)); }

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.VINDICATOR_CELEBRATE;
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (super.isAlliedTo(entity)) return true;
        if (entity instanceof LivingEntity && ((LivingEntity)entity).getMobType() == CreatureAttribute.ILLAGER) return (this.getTeam() == null && entity.getTeam() == null);
        return false;
    }

    @Override
    protected void customServerAiStep() {
        if (!this.isNoAi() && GroundPathHelper.hasGroundPathNavigation(this))
            ((GroundPathNavigator)this.getNavigation()).setCanOpenDoors(((ServerWorld)this.level).isRaided(this.blockPosition()));
        super.customServerAiStep();
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld level, DifficultyInstance difficulty, SpawnReason spawntype, @Nullable ILivingEntityData data, @Nullable CompoundNBT tag) {
        ILivingEntityData spawngroupdata = super.finalizeSpawn(level, difficulty, spawntype, data, tag);
        ((GroundPathNavigator)this.getNavigation()).setCanOpenDoors(true);
        this.populateDefaultEquipmentSlots(difficulty);
        this.populateDefaultEquipmentEnchantments(difficulty);
        return spawngroupdata;
    }

    private static class SkirmisherMeleeAttackGoal extends MeleeAttackGoal {
        public SkirmisherMeleeAttackGoal(SkirmisherEntity entity) {
            super(entity, 1.1D, false);
        }

        @Override
        protected double getAttackReachSqr(LivingEntity livingentity) {
            if (this.mob.getVehicle() instanceof RavagerEntity) {
                float f = this.mob.getVehicle().getBbWidth() - 0.1F;
                return (f * 2.0F * f * 2.0F + livingentity.getBbWidth());
            }
            return super.getAttackReachSqr(livingentity);
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.VINDICATOR_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.VINDICATOR_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.VINDICATOR_HURT;
    }

    private static class SkirmisherBreakDoorGoal extends BreakDoorGoal {
        public SkirmisherBreakDoorGoal(MobEntity mob) {
            super(mob, 6, SkirmisherEntity.DOOR_BREAKING_PREDICATE);
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canContinueToUse() {
            return this.getMob().hasActiveRaid() && super.canContinueToUse();
        }

        @Override
        public boolean canUse() {
            return this.getMob().hasActiveRaid() && this.getMob().random.nextInt(10) == 0 && super.canUse();
        }

        public void start() {
            super.start();
            this.mob.setNoActionTime(0);
        }

        private SkirmisherEntity getMob(){
            return (SkirmisherEntity) this.mob;
        }
    }
}
