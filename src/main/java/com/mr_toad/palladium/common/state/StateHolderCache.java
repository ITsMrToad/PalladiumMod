package com.mr_toad.palladium.common.state;

import com.mr_toad.palladium.common.util.GoodImmutableTableCache;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public class StateHolderCache {

    public static final GoodImmutableTableCache<Property<?>, Comparable<?>, BlockState> BLOCK_STATE_TABLE = new GoodImmutableTableCache<>();
    public static final GoodImmutableTableCache<Property<?>, Comparable<?>, FluidState> FLUID_STATE_TABLE = new GoodImmutableTableCache<>();

    @SuppressWarnings("unchecked")
    public static <S, O> GoodImmutableTableCache<Property<?>, Comparable<?>, S> getTableCache(O owner) {
        if (owner instanceof Block) {
            return (GoodImmutableTableCache<Property<?>, Comparable<?>, S>) BLOCK_STATE_TABLE;
        } else if (owner instanceof Fluid) {
            return (GoodImmutableTableCache<Property<?>, Comparable<?>, S>) FLUID_STATE_TABLE;
        } else {
            throw new IllegalArgumentException("");
        }
    }

}
