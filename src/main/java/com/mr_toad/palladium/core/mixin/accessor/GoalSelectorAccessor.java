package com.mr_toad.palladium.core.mixin.accessor;

import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import java.util.function.Supplier;

@Mixin(GoalSelector.class)
public interface GoalSelectorAccessor {

    @Accessor("profiler")
    Supplier<ProfilerFiller> getProfiler();

    @Accessor("lockedFlags")
    Map<Goal.Flag, WrappedGoal> getLockedFlags();

}
