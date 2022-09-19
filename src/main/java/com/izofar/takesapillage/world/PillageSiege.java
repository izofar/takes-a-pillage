package com.izofar.takesapillage.world;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.util.ModLists;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.raid.Raid;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.ISpecialSpawner;

import javax.annotation.Nullable;

public class PillageSiege implements ISpecialSpawner {
    private boolean hasSetupSiege;
    private State siegeState = State.SIEGE_DONE;
    private int pillagersToSpawn;
    private int nextSpawnTime;
    private int spawnX;
    private int spawnY;
    private int spawnZ;

    @Override
    public int tick(ServerWorld serverlevel, boolean spawnEnemies, boolean spawnFriendlies) {
        if (!serverlevel.isDay() && spawnEnemies) {
            float f = serverlevel.getTimeOfDay(0.0F);
            if ((double)f == 0.5D) {
                this.siegeState = serverlevel.random.nextInt(10) == 0 ? State.SIEGE_TONIGHT : State.SIEGE_DONE;
            }
            if (this.siegeState == State.SIEGE_DONE) {
                return 0;
            } else {
                if (!this.hasSetupSiege) {
                    if (!this.tryToSetupSiege(serverlevel)) {
                        return 0;
                    }
                    this.hasSetupSiege = true;
                }
                if (this.nextSpawnTime > 0) {
                    --this.nextSpawnTime;
                    return 0;
                } else {
                    this.nextSpawnTime = 2;
                    if (this.pillagersToSpawn > 0) {
                        this.trySpawn(serverlevel);
                        --this.pillagersToSpawn;
                    } else {
                        this.siegeState = State.SIEGE_DONE;
                    }
                    return 1;
                }
            }
        } else {
            this.siegeState = State.SIEGE_DONE;
            this.hasSetupSiege = false;
            return 0;
        }
    }

    private boolean tryToSetupSiege(ServerWorld serverLevel) {
        for (PlayerEntity player : serverLevel.players()) {
            if (!player.isSpectator()) {
                BlockPos blockpos = player.blockPosition();
                if (serverLevel.isVillage(blockpos) && serverLevel.getBiome(blockpos).getBiomeCategory() != Biome.Category.MUSHROOM) {
                    for (int i = 0; i < 10; ++i) {
                        float f = serverLevel.random.nextFloat() * ((float)Math.PI * 2F);
                        this.spawnX = blockpos.getX() + MathHelper.floor(MathHelper.cos(f) * 32.0F);
                        this.spawnY = blockpos.getY();
                        this.spawnZ = blockpos.getZ() + MathHelper.floor(MathHelper.sin(f) * 32.0F);
                        Vector3d siegeLocation = this.findRandomSpawnPos(serverLevel, new BlockPos(this.spawnX, this.spawnY, this.spawnZ));
                        if (siegeLocation != null) {
                            this.nextSpawnTime = 0;
                            this.pillagersToSpawn = serverLevel.random.nextInt(6) + 4;
                            break;
                        }
                    }
                    if (serverLevel.getTimeOfDay(serverLevel.dayTime()) > MathHelper.frac(11.0D / 24.0D)) {
                        serverLevel.playSound(null, blockpos.getX(), blockpos.getY(), blockpos.getZ(), SoundEvents.RAID_HORN, SoundCategory.NEUTRAL, 64.0F, 1.0F);
                        serverLevel.playSound(null, blockpos.getX(), blockpos.getY(), blockpos.getZ(), SoundEvents.BELL_BLOCK, SoundCategory.BLOCKS, 2.0F, 1.0F);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private void trySpawn(ServerWorld serverLevel) {
        Vector3d vec3 = findRandomSpawnPos(serverLevel, new BlockPos(this.spawnX, this.spawnY, this.spawnZ));
        if (vec3 != null) {
            AbstractIllagerEntity pillager;
            try {
                pillager = ModLists.getPillagerSiegeList().getRandom(serverLevel.random).get().create(serverLevel);
                assert pillager != null;
                pillager.setPersistenceRequired();
                if (serverLevel.random.nextInt(6) < 1) {
                    pillager.setItemSlot(EquipmentSlotType.HEAD, Raid.getLeaderBannerInstance());
                    pillager.setDropChance(EquipmentSlotType.HEAD, 2.0F);
                }
                pillager.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(pillager.blockPosition()), SpawnReason.EVENT, null, null);
            } catch (Exception exception) {
                TakesAPillageMod.LOGGER.warn("Failed to create pillager for pillage siege at {}", vec3, exception);
                return;
            }
            pillager.moveTo(vec3.x, vec3.y, vec3.z, serverLevel.random.nextFloat() * 360.0F, 0.0F);
            serverLevel.addFreshEntityWithPassengers(pillager);
        }
    }

    @Nullable
    private Vector3d findRandomSpawnPos(ServerWorld serverLevel, BlockPos blockPos) {
        for (int i = 0; i < 10; i++) {
            int j = blockPos.getX() + serverLevel.random.nextInt(16) - 8;
            int k = blockPos.getZ() + serverLevel.random.nextInt(16) - 8;
            int l = serverLevel.getHeight(Heightmap.Type.WORLD_SURFACE, j, k);
            BlockPos blockpos = new BlockPos(j, l, k);
            if (serverLevel.isVillage(blockpos) && MonsterEntity.checkMonsterSpawnRules(EntityType.PILLAGER, serverLevel, SpawnReason.EVENT, blockpos, serverLevel.random))
                return Vector3d.atBottomCenterOf(blockpos);
        }
        return null;
    }

    enum State {
        SIEGE_TONIGHT, SIEGE_DONE
    }
}
