package com.izofar.takesapillage.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class Archer extends AbstractIllager implements RangedAttackMob {

    private static final EntityDataAccessor<Optional<UUID>> DATA_TARGET_UUID = SynchedEntityData.defineId(Archer.class, EntityDataSerializers.OPTIONAL_UUID);

    private final RangedBowAttackGoal<Archer> bowGoal = new RangedBowAttackGoal<>(this, 1.0D, 20, 15.0F);

    public Archer(EntityType<? extends AbstractIllager> entitytype, Level world) {
        super(entitytype, world);
        reassessWeaponGoal();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.36D)
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.FOLLOW_RANGE, 40.0D);
    }
    
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new HoldGroundAttackGoal(this, 10.0F));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 15.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 15.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
    }

    @Override
    public AbstractIllager.IllagerArmPose getArmPose() {
        return this.getTarget() != null ? IllagerArmPose.CROSSBOW_HOLD : IllagerArmPose.NEUTRAL;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TARGET_UUID, Optional.empty());
    }

    @Override
    public void applyRaidBuffs(int round, boolean b) {}

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.PILLAGER_CELEBRATE;
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) { this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW)); }

    @Override
    public void performRangedAttack(LivingEntity entity, float f) {
        ItemStack itemstack = this.getProjectile(this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof BowItem)));
        AbstractArrow abstractarrow = getArrow(itemstack, f);
        if (this.getMainHandItem().getItem() instanceof BowItem bowItem) abstractarrow = bowItem.customArrow(abstractarrow);
        double d0 = entity.getX() - this.getX();
        double d1 = entity.getY(0.3333333333333333D) - abstractarrow.getY();
        double d2 = entity.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        abstractarrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (14 - this.level.getDifficulty().getId() * 4));
        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level.addFreshEntity(abstractarrow);
    }

    protected AbstractArrow getArrow(ItemStack itemstack, float f) { return ProjectileUtil.getMobArrow(this, itemstack, f); }

    @Override
    public boolean canFireProjectileWeapon(ProjectileWeaponItem item) {
        return (item == Items.BOW);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelaccessor, DifficultyInstance difficulty, MobSpawnType spawntype, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
        data = super.finalizeSpawn(levelaccessor, difficulty, spawntype, data, tag);
        ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
        this.populateDefaultEquipmentSlots(difficulty);
        this.populateDefaultEquipmentEnchantments(difficulty);
        this.reassessWeaponGoal();
        this.setCanPickUpLoot((this.random.nextFloat() < 0.55F * difficulty.getSpecialMultiplier()));
        return data;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        reassessWeaponGoal();
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack itemstack) {
        super.setItemSlot(slot, itemstack);
        if (!this.level.isClientSide)
            reassessWeaponGoal();
    }

    protected void reassessWeaponGoal() {
        if (!this.level.isClientSide) {
            this.goalSelector.removeGoal(this.bowGoal);
            ItemStack itemstack = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof BowItem));
            if (itemstack.is(Items.BOW)) {
                int i = this.level.getDifficulty() == Difficulty.HARD ? 15 : 25;
                this.bowGoal.setMinAttackInterval(i);
                this.goalSelector.addGoal(4, this.bowGoal);
            }
        }
    }

    @Nullable
    @Override
    public LivingEntity getTarget(){
        try{
            UUID uuid = this.getTargetUUID();
            return uuid == null ? super.getTarget() : this.level.getPlayerByUUID(uuid);
        }catch(IllegalArgumentException exception){
            return super.getTarget();
        }
    }

    @Override
    public void setTarget(@Nullable LivingEntity target){
        super.setTarget(target);
        this.setTargetUUID(target == null ? null : target.getUUID());
    }

    @Nullable
    private UUID getTargetUUID(){
        return this.entityData.get(DATA_TARGET_UUID).orElse(null);
    }

    private void setTargetUUID(@Nullable UUID uuid){
        this.entityData.set(DATA_TARGET_UUID, Optional.ofNullable(uuid));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PILLAGER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33306_) {
        return SoundEvents.PILLAGER_HURT;
    }
}
