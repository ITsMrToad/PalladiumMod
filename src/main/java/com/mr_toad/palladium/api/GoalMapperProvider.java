package com.mr_toad.palladium.api;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.mr_toad.lib.mtjava.util.func.SelfFunction;
import com.mr_toad.palladium.core.Palladium;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.goal.WrappedGoal;

import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public interface GoalMapperProvider {

    Map<Class<?>, Constructor<?>> WRAPPED = Maps.newConcurrentMap();

    boolean canRun();

    void run(Mob entity, ResourceLocation location);

    default void runIfCan(Mob entity, ResourceLocation location) {
        if(this.canRun()) {
            this.run(entity, location);
        }
    }

    default boolean removeAllTasksOfClass(Mob entity, Class<?> classToRemove) {
        boolean found = false;
        Set<WrappedGoal> availableGoals = entity.goalSelector.getAvailableGoals();
        Iterator<WrappedGoal> iterator = availableGoals.iterator();
        while(iterator.hasNext()) {
            WrappedGoal task = iterator.next();
            if(task.getGoal().getClass() == classToRemove) {
                found = true;
                iterator.remove();
            }
        }
        return found;
    }

    @CanIgnoreReturnValue
    default int tryAndReplaceAllTasks(Mob entity, GoalSelector goalSelector, Class<?> toMatch, SelfFunction<Goal> action) {
        int count = 0;
        Set<WrappedGoal> newAvailableGoals = Sets.newLinkedHashSet();
        Set<WrappedGoal> oldAvailableGoals = goalSelector.getAvailableGoals();

        for(WrappedGoal entry : oldAvailableGoals) {
            if(toMatch == entry.getGoal().getClass()) {
                Goal newAI = action.apply(entry.getGoal());
                if(newAI != null) {
                    newAvailableGoals.add(new WrappedGoal(entry.getPriority(), newAI));
                    count++;
                } else {
                    Palladium.LOGGER.warn("Failed replace {} to {}", entity.getClass().getName(), toMatch.getName());
                    newAvailableGoals.add(entry);
                }
            } else {
                newAvailableGoals.add(entry);
            }
        }

        if(count>0) {
            oldAvailableGoals.clear();
            oldAvailableGoals.addAll(newAvailableGoals);
        }

        return count;
    }

}
