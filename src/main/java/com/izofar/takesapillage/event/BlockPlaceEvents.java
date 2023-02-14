package com.izofar.takesapillage.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface BlockPlaceEvents {
    Event<BlockPlaceEvents> EVENT = EventFactory.createArrayBacked(BlockPlaceEvents.class, callbacks -> (level, pos, state, placer, stack) -> {
        for(BlockPlaceEvents callback : callbacks) {
            callback.onBlockPlaced(level, pos, state, placer, stack);
        }
    });

    void onBlockPlaced(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack);
}
