package com.izofar.takesapillage.entity;

import com.izofar.takesapillage.entity.ai.ModIllagerAI;
import com.izofar.takesapillage.entity.ai.ShieldGoal;
import com.izofar.takesapillage.init.ModSoundEvents;
import com.izofar.takesapillage.util.ForgeUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Legioner extends AbstractIllager implements ShieldedMob {
    private static final EntityDataAccessor<Boolean> DATA_IS_SHIELDED = SynchedEntityData.defineId(Legioner.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_SHIELD_HAND = SynchedEntityData.defineId(Legioner.class, EntityDataSerializers.BOOLEAN); // True for Main Hand, False for Offhand
    private static final EntityDataAccessor<Integer> DATA_SHIELD_COOLDOWN = SynchedEntityData.defineId(Legioner.class, EntityDataSerializers.INT);

    private static final UUID SPEED_MODIFIER_ATTACKING_UUID = UUID.fromString("3520BCE0-D755-458F-944B-A528DB8EF9DC");
    private static final AttributeModifier SPEED_MODIFIER_BLOCKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_UUID, "Shielded speed penalty", -0.10D, AttributeModifier.Operation.ADDITION);

    public Legioner(EntityType<? extends AbstractIllager> entitytype, Level world) { super(entitytype, world); }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.275D)
                .add(Attributes.FOLLOW_RANGE, 24.0D)
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.5D)
                .add(Attributes.ARMOR, 6.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ShieldGoal<>(this, Player.class));
        this.goalSelector.addGoal(2, new AbstractIllager.RaiderOpenDoorGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.25D, true));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_IS_SHIELDED, false);
        this.entityData.define(DATA_SHIELD_HAND, false);
        this.entityData.define(DATA_SHIELD_COOLDOWN, 0);
    }

    @Override
    public void tick(){
        super.tick();
        if(!this.level.isClientSide){
            this.decrementShieldCooldown();
        }
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelaccessor, DifficultyInstance difficulty, MobSpawnType spawntype, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
        SpawnGroupData spawngroupdata = super.finalizeSpawn(levelaccessor, difficulty, spawntype, data, tag);
        ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
        this.populateDefaultEquipmentSlots(difficulty);
        this.populateDefaultEquipmentEnchantments(difficulty);
        return spawngroupdata;
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
    }

    @Override
    public boolean doHurtTarget(Entity target){
        return ModIllagerAI.doHurtTarget(this, target, "legioner");
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (super.isAlliedTo(entity)) return true;
        else if (entity instanceof LivingEntity livingEntity && livingEntity.getMobType() == MobType.ILLAGER) return (this.getTeam() == null && entity.getTeam() == null);
        return false;
    }

    @Override
    public AbstractIllager.IllagerArmPose getArmPose() {
        if (this.isAggressive()) return AbstractIllager.IllagerArmPose.ATTACKING;
        return this.isCelebrating() ? AbstractIllager.IllagerArmPose.CELEBRATING : null;
    }

    @Override
    public void applyRaidBuffs(int round, boolean b) {}

    @Override
    public void knockback(double x, double y, double z){
        if(!this.isUsingShield()){
            super.knockback(x, y, z);
        }else{
            this.playSound(SoundEvents.SHIELD_BLOCK, 1.0F, 0.8F + this.level.random.nextFloat() * 0.4F);
        }
    }

    @Override
    protected void blockUsingShield(LivingEntity attacker) {
        super.blockUsingShield(attacker);
        if (ForgeUtils.canDisableShield(attacker.getMainHandItem())) {
            this.disableShield();
        }
    }

    private void disableShield() {
        this.setShieldCooldown(60);
        this.stopUsingShield();
        this.level.broadcastEntityEvent(this, (byte)30);
        this.playSound(SoundEvents.SHIELD_BREAK, 0.8F, 0.8F + this.level.random.nextFloat() * 0.4F);
    }

    @Override
    public boolean isShieldDisabled(){
        return this.getShieldCooldown() > 0;
    }

    @Override
    public void startUsingShield(){
        if(this.isUsingShield() || this.isShieldDisabled()) return;
        for(InteractionHand interactionhand : InteractionHand.values()) {
            if (this.getItemInHand(interactionhand).is(Items.SHIELD)) {
                this.startUsingItem(interactionhand);
                this.setUsingShield(true);
                this.setShieldMainhand(interactionhand == InteractionHand.MAIN_HAND);
                AttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
                if (attributeinstance != null && !attributeinstance.hasModifier(SPEED_MODIFIER_BLOCKING)) {
                    attributeinstance.addTransientModifier(SPEED_MODIFIER_BLOCKING);
                }
            }
        }
    }

    @Override
    public void stopUsingShield(){
        if(!this.isUsingShield()) return;
        for(InteractionHand interactionhand : InteractionHand.values()) {
            if (this.getItemInHand(interactionhand).is(Items.SHIELD)) {
                this.stopUsingItem();
                this.setUsingShield(false);
                AttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
                if(attributeinstance != null)
                    attributeinstance.removeModifier(SPEED_MODIFIER_BLOCKING);
            }
        }
    }

    public boolean isUsingShield(){
        return this.entityData.get(DATA_IS_SHIELDED);
    }

    public void setUsingShield(boolean isShielded){
        this.entityData.set(DATA_IS_SHIELDED, isShielded);
    }

    private boolean isShieldMainhand(){
        return this.entityData.get(DATA_SHIELD_HAND);
    }

    private void setShieldMainhand(boolean isShieldedMainHand){
        this.entityData.set(DATA_SHIELD_HAND, isShieldedMainHand);
    }

    private int getShieldCooldown(){
        return this.entityData.get(DATA_SHIELD_COOLDOWN);
    }

    private void setShieldCooldown(int newShieldCooldown){
        this.entityData.set(DATA_SHIELD_COOLDOWN, newShieldCooldown);
    }

    private void decrementShieldCooldown(){
        this.setShieldCooldown(Math.max(this.getShieldCooldown() - 1, 0));
    }

    public InteractionHand getShieldHand(){
        return this.isUsingShield() ? (this.isShieldMainhand() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND) : null;
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return ModSoundEvents.LEGIONER_CELEBRATE;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.LEGIONER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.LEGIONER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33306_) {
        return ModSoundEvents.LEGIONER_HURT;
    }

}
