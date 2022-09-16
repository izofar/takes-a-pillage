package com.izofar.takesapillage.entity;

import com.izofar.takesapillage.entity.ai.ModIllagerAI;
import com.izofar.takesapillage.entity.ai.ShieldGoal;
import com.izofar.takesapillage.init.ModSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.UUID;

public class LegionerEntity extends AbstractIllagerEntity implements ShieldedMob{

    private static final DataParameter<Boolean> DATA_IS_SHIELDED = EntityDataManager.defineId(LegionerEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> DATA_SHIELD_HAND = EntityDataManager.defineId(LegionerEntity.class, DataSerializers.BOOLEAN); // True for Main Hand, False for Offhand
    private static final DataParameter<Integer> DATA_SHIELD_COOLDOWN = EntityDataManager.defineId(LegionerEntity.class, DataSerializers.INT);

    private static final UUID SPEED_MODIFIER_ATTACKING_UUID = UUID.fromString("3520BCE0-D755-458F-944B-A528DB8EF9DC");
    private static final AttributeModifier SPEED_MODIFIER_BLOCKING = new AttributeModifier(SPEED_MODIFIER_ATTACKING_UUID, "Shielded speed penalty", -0.10D, AttributeModifier.Operation.ADDITION);

    public LegionerEntity(EntityType<? extends AbstractIllagerEntity> entitytype, World world) { super(entitytype, world); }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MonsterEntity.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.275D)
                .add(Attributes.FOLLOW_RANGE, 24.0D)
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.5D)
                .add(Attributes.ARMOR, 6.0D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new ShieldGoal<>(this, PlayerEntity.class));
        this.goalSelector.addGoal(2, new AbstractIllagerEntity.RaidOpenDoorGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.25D, true));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, AbstractRaiderEntity.class)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
        this.goalSelector.addGoal(8, new RandomWalkingGoal(this, 0.6D));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
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
    public ILivingEntityData finalizeSpawn(IServerWorld level, DifficultyInstance difficulty, SpawnReason spawntype, @Nullable ILivingEntityData data, @Nullable CompoundNBT tag) {
        ILivingEntityData livingEntityData = super.finalizeSpawn(level, difficulty, spawntype, data, tag);
        ((GroundPathNavigator)this.getNavigation()).setCanOpenDoors(true);
        this.populateDefaultEquipmentSlots(difficulty);
        this.populateDefaultEquipmentEnchantments(difficulty);
        return livingEntityData;
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance difficulty) {
        this.setItemSlot(EquipmentSlotType.MAINHAND, new ItemStack(Items.IRON_SWORD));
        this.setItemSlot(EquipmentSlotType.OFFHAND, new ItemStack(Items.SHIELD));
    }

    @Override
    public boolean doHurtTarget(Entity target){
        return ModIllagerAI.doHurtTarget(this, target, "legioner");
    }

    @Override
    public boolean isAlliedTo(Entity entity) {
        if (super.isAlliedTo(entity)) return true;
        else if (entity instanceof LivingEntity && ((LivingEntity) entity).getMobType() == CreatureAttribute.ILLAGER) return (this.getTeam() == null && entity.getTeam() == null);
        return false;
    }

    @Override
    public AbstractIllagerEntity.ArmPose getArmPose() {
        if (this.isAggressive()) return AbstractIllagerEntity.ArmPose.ATTACKING;
        return this.isCelebrating() ? AbstractIllagerEntity.ArmPose.CELEBRATING : null;
    }

    @Override
    public void applyRaidBuffs(int round, boolean b) {}

    @Override
    public void knockback(float strength, double x, double z){
        if(!this.isUsingShield()){
            super.knockback(strength, x, z);
        }else{
            this.playSound(SoundEvents.SHIELD_BLOCK, 1.0F, 0.8F + this.level.random.nextFloat() * 0.4F);
        }
    }

    @Override
    protected void blockUsingShield(LivingEntity attacker) {
        super.blockUsingShield(attacker);
        if (attacker.getMainHandItem().canDisableShield(this.useItem, this, attacker)) {
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
        for(Hand interactionhand : Hand.values()) {
            if (this.getItemInHand(interactionhand).getItem() == Items.SHIELD) {
                this.startUsingItem(interactionhand);
                this.setUsingShield(true);
                this.setShieldMainhand(interactionhand == Hand.MAIN_HAND);
                ModifiableAttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
                if (attributeinstance != null && !attributeinstance.hasModifier(SPEED_MODIFIER_BLOCKING)) {
                    attributeinstance.addTransientModifier(SPEED_MODIFIER_BLOCKING);
                }
            }
        }
    }

    @Override
    public void stopUsingShield(){
        if(!this.isUsingShield()) return;
        for(Hand hand : Hand.values()) {
            if (this.getItemInHand(hand).getItem() == Items.SHIELD) {
                this.stopUsingItem();
                this.setUsingShield(false);
                ModifiableAttributeInstance attributeinstance = this.getAttribute(Attributes.MOVEMENT_SPEED);
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

    public Hand getShieldHand(){
        return this.isUsingShield() ? (this.isShieldMainhand() ? Hand.MAIN_HAND : Hand.OFF_HAND) : null;
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return ModSoundEvents.LEGIONER_CELEBRATE.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSoundEvents.LEGIONER_AMBIENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSoundEvents.LEGIONER_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSoundEvents.LEGIONER_HURT.get();
    }

}
