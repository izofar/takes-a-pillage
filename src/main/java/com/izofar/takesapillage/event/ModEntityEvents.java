package com.izofar.takesapillage.event;

import com.google.common.collect.ImmutableList;
import com.izofar.takesapillage.entity.ClayGolem;
import com.izofar.takesapillage.init.ModEntityTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.LinkedList;
import java.util.Queue;

public abstract class ModEntityEvents {

    private static final Queue<ClayGolem> golem_queue = new LinkedList<>();

    @SubscribeEvent
    public static void replaceNaturallySpawningIronGolemWithClayGolem(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof IronGolem irongolem
                && !(event.getEntity() instanceof ClayGolem)
                && !event.getWorld().isClientSide
                && event.getWorld() instanceof ServerLevel world) {
            if (!irongolem.isPlayerCreated()) {
                ClayGolem claygolementity = ModEntityTypes.CLAY_GOLEM.get().create(world);
                claygolementity.moveTo(irongolem.position());
                golem_queue.add(claygolementity);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void checkForUnSpawnedGolem(TickEvent.WorldTickEvent event) {
        Level level = event.world;
        if (level instanceof ServerLevel world) {
            Queue<ClayGolem> remove_golem_queue = new LinkedList<>();
            for (ClayGolem entity : golem_queue) {
                if (world.isAreaLoaded(entity.blockPosition(), 0)) {
                    world.addFreshEntity(entity);
                    remove_golem_queue.add(entity);
                }
            }
            golem_queue.removeAll(remove_golem_queue);
        }
    }

    @SubscribeEvent
    public static void preventMilkFromRemovingBadOmen(PotionEvent.PotionAddedEvent event) {
        if (event.getPotionEffect().getEffect().equals(MobEffects.BAD_OMEN))
            event.getPotionEffect().setCurativeItems(ImmutableList.of());
    }
}
