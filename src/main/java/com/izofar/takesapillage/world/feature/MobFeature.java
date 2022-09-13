package com.izofar.takesapillage.world.feature;

import com.izofar.takesapillage.util.MobWeightedEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.function.Supplier;

public class MobFeature<T extends Mob> extends Feature<NoneFeatureConfiguration> {
    private final Supplier<WeightedRandomList<MobWeightedEntry<EntityType<? extends T>>>> entityTypes;

    public MobFeature(Supplier<WeightedRandomList<MobWeightedEntry<EntityType<? extends T>>>> entityTypes) {
        super(NoneFeatureConfiguration.CODEC);
        this.entityTypes = entityTypes;
    }

    public MobFeature(EntityType<? extends T> entityType) {
        super(NoneFeatureConfiguration.CODEC);
        this.entityTypes = (() -> WeightedRandomList.create(new MobWeightedEntry(entityType, 1)));
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        BlockPos position = context.origin().below();
        Mob entity = this.entityTypes.get().getRandom(context.random()).get().getData().create(context.level().getLevel());
        if (entity == null)
            return false;
        entity.moveTo(position.getX() + 0.5D, position.getY(), position.getZ() + 0.5D, 0.0F, 0.0F);
        entity.finalizeSpawn(context.level(), context.level().getCurrentDifficultyAt(position), MobSpawnType.STRUCTURE, null, null);
        entity.setPersistenceRequired();
        context.level().addFreshEntity(entity);
        return true;
    }
}
