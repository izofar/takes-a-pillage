package com.izofar.takesapillage.util;


import com.google.common.collect.ImmutableList;
import com.izofar.takesapillage.init.ModEntityTypes;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.AbstractIllager;

public abstract class ModLists {
    private static WeightedRandomList<MobWeightedEntry<EntityType<? extends AbstractIllager>>> PILLAGER_CAMP_LIST;

    private static WeightedRandomList<MobWeightedEntry<EntityType<? extends AbstractIllager>>> PILLAGER_SIEGE_LIST;

    private static WeightedRandomList<MobWeightedEntry<EntityType<? extends AbstractIllager>>> RANGED_ILLAGER_LIST;

    private static WeightedRandomList<MobWeightedEntry<EntityType<? extends Animal>>> LIVESTOCK_LIST;

    private static WeightedRandomList<MobWeightedEntry<EntityType<? extends Mob>>> PRISONER_LIST;

    private static WeightedRandomList<MobWeightedEntry<EntityType<? extends AbstractIllager>>> BASTILLE_LIST;

    private static WeightedRandomList<MobWeightedEntry<EntityType<? extends Mob>>> CAPTIVE_LIST;

    public static void setupEntityLists() {
        PILLAGER_CAMP_LIST = WeightedRandomList.create(ImmutableList.of(new MobWeightedEntry(EntityType.PILLAGER, 15), new MobWeightedEntry(ModEntityTypes.SKIRMISHER.get(), 12), new MobWeightedEntry(ModEntityTypes.ARCHER.get(), 8), new MobWeightedEntry(EntityType.VINDICATOR, 5), new MobWeightedEntry(EntityType.EVOKER, 1)));
        PILLAGER_SIEGE_LIST = WeightedRandomList.create(ImmutableList.of(new MobWeightedEntry(EntityType.PILLAGER, 8), new MobWeightedEntry(ModEntityTypes.SKIRMISHER.get(), 6), new MobWeightedEntry(ModEntityTypes.ARCHER.get(), 6), new MobWeightedEntry(ModEntityTypes.LEGIONER.get(), 4), new MobWeightedEntry(EntityType.VINDICATOR, 3)));
        RANGED_ILLAGER_LIST = WeightedRandomList.create(ImmutableList.of(new MobWeightedEntry(EntityType.PILLAGER, 1), new MobWeightedEntry(ModEntityTypes.ARCHER.get(), 2)));
        LIVESTOCK_LIST = WeightedRandomList.create(ImmutableList.of(new MobWeightedEntry(EntityType.COW, 4), new MobWeightedEntry(EntityType.CHICKEN, 3), new MobWeightedEntry(EntityType.SHEEP, 6), new MobWeightedEntry(EntityType.DONKEY, 1), new MobWeightedEntry(EntityType.HORSE, 2)));
        PRISONER_LIST = WeightedRandomList.create(ImmutableList.of(new MobWeightedEntry(EntityType.IRON_GOLEM, 4), new MobWeightedEntry(EntityType.VILLAGER, 3), new MobWeightedEntry(ModEntityTypes.CLAY_GOLEM.get(), 3)));
        BASTILLE_LIST = WeightedRandomList.create(ImmutableList.of(new MobWeightedEntry(ModEntityTypes.LEGIONER.get(), 15), new MobWeightedEntry(ModEntityTypes.SKIRMISHER.get(), 12), new MobWeightedEntry(ModEntityTypes.ARCHER.get(), 8), new MobWeightedEntry(EntityType.PILLAGER, 7), new MobWeightedEntry(EntityType.VINDICATOR, 5), new MobWeightedEntry(EntityType.EVOKER, 1)));
        CAPTIVE_LIST = WeightedRandomList.create(ImmutableList.of(new MobWeightedEntry(EntityType.VILLAGER, 5), new MobWeightedEntry(ModEntityTypes.CLAY_GOLEM.get(), 3), new MobWeightedEntry(EntityType.IRON_GOLEM, 2)));
    }

    public static WeightedRandomList<MobWeightedEntry<EntityType<? extends AbstractIllager>>> get_pillager_siege_list() {
        return PILLAGER_SIEGE_LIST;
    }

    public static WeightedRandomList<MobWeightedEntry<EntityType<? extends AbstractIllager>>> get_pillager_camp_list() {
        return PILLAGER_CAMP_LIST;
    }

    public static WeightedRandomList<MobWeightedEntry<EntityType<? extends AbstractIllager>>> get_ranged_illager_list() {
        return RANGED_ILLAGER_LIST;
    }

    public static WeightedRandomList<MobWeightedEntry<EntityType<? extends Animal>>> get_livestock_list() {
        return LIVESTOCK_LIST;
    }

    public static WeightedRandomList<MobWeightedEntry<EntityType<? extends Mob>>> get_prisoner_list() {
        return PRISONER_LIST;
    }

    public static WeightedRandomList<MobWeightedEntry<EntityType<? extends AbstractIllager>>> get_bastille_list() {
        return BASTILLE_LIST;
    }

    public static WeightedRandomList<MobWeightedEntry<EntityType<? extends Mob>>> get_captive_list() {
        return CAPTIVE_LIST;
    }
}
