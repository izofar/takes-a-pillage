package com.izofar.takesapillage.world;

import com.izofar.takesapillage.TakesAPillageMod;
import com.izofar.takesapillage.config.ModCommonConfigs;
import com.izofar.takesapillage.util.ModLists;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class PillageSiege implements CustomSpawner {
    private boolean hasSetupSiege;
    private State siegeState = State.SIEGE_DONE;
    private int pillagersToSpawn;
    private int nextSpawnTime;
    private int spawnX;
    private int spawnY;
    private int spawnZ;

    @Override
    public int tick(ServerLevel serverlevel, boolean spawnEnemies, boolean spawnFriendlies) {
        if (!serverlevel.isDay() && spawnEnemies && ModCommonConfigs.DO_PILLAGE_SIEGES) {
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

    private boolean tryToSetupSiege(ServerLevel serverLevel) {
        for (Player player : serverLevel.players()) {
            if (!player.isSpectator()) {
                BlockPos blockpos = player.blockPosition();
                if (serverLevel.isVillage(blockpos) && Biome.getBiomeCategory(serverLevel.getBiome(blockpos)) != Biome.BiomeCategory.MUSHROOM) {
                    for (int i = 0; i < 10; ++i) {
                        float f = serverLevel.random.nextFloat() * ((float)Math.PI * 2F);
                        this.spawnX = blockpos.getX() + Mth.floor(Mth.cos(f) * 32.0F);
                        this.spawnY = blockpos.getY();
                        this.spawnZ = blockpos.getZ() + Mth.floor(Mth.sin(f) * 32.0F);
                        Vec3 siegeLocation = this.findRandomSpawnPos(serverLevel, new BlockPos(this.spawnX, this.spawnY, this.spawnZ));
                        if (siegeLocation != null) {
                            this.nextSpawnTime = 0;
                            this.pillagersToSpawn = serverLevel.random.nextInt(6) + 4;
                            break;
                        }
                    }
                    if (serverLevel.getTimeOfDay(serverLevel.dayTime()) > Mth.frac(11.0D / 24.0D)) {
                        serverLevel.playSound(null, blockpos.getX(), blockpos.getY(), blockpos.getZ(), SoundEvents.RAID_HORN, SoundSource.NEUTRAL, 64.0F, 1.0F);
                        serverLevel.playSound(null, blockpos.getX(), blockpos.getY(), blockpos.getZ(), SoundEvents.BELL_BLOCK, SoundSource.BLOCKS, 2.0F, 1.0F);
                        serverLevel.gameEvent(null, GameEvent.RING_BELL, blockpos);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private void trySpawn(ServerLevel serverLevel) {
        Vec3 vec3 = findRandomSpawnPos(serverLevel, new BlockPos(this.spawnX, this.spawnY, this.spawnZ));
        if (vec3 != null) {
            AbstractIllager pillager;
            try {
                pillager = ModLists.get_pillager_siege_list().getRandom(serverLevel.random).get().getData().create(serverLevel);
                pillager.setPersistenceRequired();
                if (serverLevel.random.nextInt(6) < 1) {
                    pillager.setItemSlot(EquipmentSlot.HEAD, Raid.getLeaderBannerInstance());
                    pillager.setDropChance(EquipmentSlot.HEAD, 2.0F);
                }
                pillager.finalizeSpawn(serverLevel, serverLevel.getCurrentDifficultyAt(pillager.blockPosition()), MobSpawnType.EVENT, null, null);
            } catch (Exception exception) {
                TakesAPillageMod.LOGGER.warn("Failed to create pillager for pillage siege at {}", vec3, exception);
                return;
            }
            pillager.moveTo(vec3.x, vec3.y, vec3.z, serverLevel.random.nextFloat() * 360.0F, 0.0F);
            serverLevel.addFreshEntityWithPassengers(pillager);
        }
    }

    @Nullable
    private Vec3 findRandomSpawnPos(ServerLevel serverLevel, BlockPos blockPos) {
        for (int i = 0; i < 10; i++) {
            int j = blockPos.getX() + serverLevel.random.nextInt(16) - 8;
            int k = blockPos.getZ() + serverLevel.random.nextInt(16) - 8;
            int l = serverLevel.getHeight(Heightmap.Types.WORLD_SURFACE, j, k);
            BlockPos blockpos = new BlockPos(j, l, k);
            if (serverLevel.isVillage(blockpos) && Monster.checkMonsterSpawnRules(EntityType.PILLAGER, serverLevel, MobSpawnType.EVENT, blockpos, serverLevel.random))
                return Vec3.atBottomCenterOf(blockpos);
        }
        return null;
    }

    enum State {
        SIEGE_TONIGHT, SIEGE_DONE
    }
}
