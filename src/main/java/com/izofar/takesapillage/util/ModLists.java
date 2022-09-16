package com.izofar.takesapillage.util;


import com.izofar.takesapillage.init.ModEntityTypes;
import com.izofar.takesapillage.util.random.WeightedEntry;
import com.izofar.takesapillage.util.random.WeightedRandomList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.AbstractIllagerEntity;
import net.minecraft.entity.passive.AnimalEntity;

import java.util.function.Supplier;

public abstract class ModLists {

    private static WeightedRandomList<Supplier<EntityType<? extends AbstractIllagerEntity>>> PILLAGER_CAMP_LIST;
    private static WeightedRandomList<Supplier<EntityType<? extends AbstractIllagerEntity>>> PILLAGER_SIEGE_LIST;
    private static WeightedRandomList<Supplier<EntityType<? extends AbstractIllagerEntity>>> RANGED_ILLAGER_LIST;
    private static WeightedRandomList<Supplier<EntityType<? extends AnimalEntity>>> LIVESTOCK_LIST;
    private static WeightedRandomList<Supplier<EntityType<? extends MobEntity>>> PRISONER_LIST;
    private static WeightedRandomList<Supplier<EntityType<? extends AbstractIllagerEntity>>> BASTILLE_LIST;
    private static WeightedRandomList<Supplier<EntityType<? extends MobEntity>>> CAPTIVE_LIST;

    public static void setupEntityLists() {
        PILLAGER_CAMP_LIST = WeightedRandomList.create(
                new WeightedEntry<>(() -> EntityType.PILLAGER, 15),
                new WeightedEntry<>(ModEntityTypes.SKIRMISHER::get, 12),
                new WeightedEntry<>(ModEntityTypes.ARCHER::get, 8),
                new WeightedEntry<>(() -> EntityType.VINDICATOR, 5),
                new WeightedEntry<>(() -> EntityType.EVOKER, 1)
        );
        PILLAGER_SIEGE_LIST = WeightedRandomList.create(
                new WeightedEntry<>(() -> EntityType.PILLAGER, 8),
                new WeightedEntry<>(ModEntityTypes.SKIRMISHER::get, 6),
                new WeightedEntry<>(ModEntityTypes.ARCHER::get, 6),
                new WeightedEntry<>(ModEntityTypes.LEGIONER::get, 4),
                new WeightedEntry<>(() -> EntityType.VINDICATOR, 3)
        );
        RANGED_ILLAGER_LIST = WeightedRandomList.create(
                new WeightedEntry<>(() -> EntityType.PILLAGER, 1),
                new WeightedEntry<>(ModEntityTypes.ARCHER::get, 2)
        );
        LIVESTOCK_LIST = WeightedRandomList.create(
                new WeightedEntry<>(() -> EntityType.COW, 4),
                new WeightedEntry<>(() -> EntityType.CHICKEN, 3),
                new WeightedEntry<>(() -> EntityType.SHEEP, 6),
                new WeightedEntry<>(() -> EntityType.DONKEY, 1),
                new WeightedEntry<>(() -> EntityType.HORSE, 2)
        );
        PRISONER_LIST = WeightedRandomList.create(
                new WeightedEntry<>(() -> EntityType.IRON_GOLEM, 4),
                new WeightedEntry<>(() -> EntityType.VILLAGER, 3),
                new WeightedEntry<>(ModEntityTypes.CLAY_GOLEM::get, 3)
        );
        BASTILLE_LIST = WeightedRandomList.create(
                new WeightedEntry<>(ModEntityTypes.LEGIONER::get, 15),
                new WeightedEntry<>(ModEntityTypes.SKIRMISHER::get, 12),
                new WeightedEntry<>(ModEntityTypes.ARCHER::get, 8),
                new WeightedEntry<>(() -> EntityType.PILLAGER, 7),
                new WeightedEntry<>(() -> EntityType.VINDICATOR, 5),
                new WeightedEntry<>(() -> EntityType.EVOKER, 1)
        );
        CAPTIVE_LIST = WeightedRandomList.create(
                new WeightedEntry<>(() -> EntityType.VILLAGER, 5),
                new WeightedEntry<>(ModEntityTypes.CLAY_GOLEM::get, 3),
                new WeightedEntry<>(() -> EntityType.IRON_GOLEM, 2)
        );
    }

    public static WeightedRandomList<Supplier<EntityType<? extends AbstractIllagerEntity>>> get_pillager_siege_list() {
        return PILLAGER_SIEGE_LIST;
    }

    public static WeightedRandomList<Supplier<EntityType<? extends AbstractIllagerEntity>>> get_pillager_camp_list() {
        return PILLAGER_CAMP_LIST;
    }

    public static WeightedRandomList<Supplier<EntityType<? extends AbstractIllagerEntity>>> get_ranged_illager_list() {
        return RANGED_ILLAGER_LIST;
    }

    public static WeightedRandomList<Supplier<EntityType<? extends AnimalEntity>>> get_livestock_list() {
        return LIVESTOCK_LIST;
    }

    public static WeightedRandomList<Supplier<EntityType<? extends MobEntity>>> get_prisoner_list() {
        return PRISONER_LIST;
    }

    public static WeightedRandomList<Supplier<EntityType<? extends AbstractIllagerEntity>>> get_bastille_list() {
        return BASTILLE_LIST;
    }

    public static WeightedRandomList<Supplier<EntityType<? extends MobEntity>>> get_captive_list() {
        return CAPTIVE_LIST;
    }
}
