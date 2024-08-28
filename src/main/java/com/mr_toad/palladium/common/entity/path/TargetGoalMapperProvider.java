package com.mr_toad.palladium.common.entity.path;

import com.mr_toad.palladium.api.GoalMapperProvider;
import com.mr_toad.palladium.common.entity.ai.goal.NearestAttackableTargetFastGoal;
import com.mr_toad.palladium.core.Palladium;
import com.mr_toad.palladium.core.mixin.accessor.NearestAttackTargetGoalAccessor;
import com.mr_toad.palladium.core.mixin.accessor.TargetGoalAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;

@SuppressWarnings({"rawtypes", "unchecked"})
public class TargetGoalMapperProvider implements GoalMapperProvider {

    @Override
    public boolean canRun() {
        return Palladium.configBoolean(cfg -> cfg.lightweightWolfAndCatAttackAi);
    }

    @Override
    public void run(Mob entity, ResourceLocation location) {
        this.replaceAttackableAll(entity);
        switch (location.getPath()) {
            case "cat", "wolf":
                replaceTamed(entity);
                break;
            default:
        }
    }

    private void replaceAttackableAll(Mob entity) {
        this.tryAndReplaceAllTasks(entity, entity.targetSelector, NearestAttackableTargetGoal.class, goal -> {
            try {
                TargetGoalAccessor targetGoalAccessor = (TargetGoalAccessor) goal;
                NearestAttackTargetGoalAccessor<?> nearestAttackTargetGoalAccessor = (NearestAttackTargetGoalAccessor<?>) goal;
                return new NearestAttackableTargetFastGoal(targetGoalAccessor.getMob(), nearestAttackTargetGoalAccessor.getTarget(), targetGoalAccessor.isMustSee(), targetGoalAccessor.isMustReach(), nearestAttackTargetGoalAccessor.getTargetingConditions());
            } catch (Exception e) {
                return null;
            }
        });
    }

    private void replaceTamed(Mob entity) {
        if (!(entity instanceof TamableAnimal)) {
            return;
        }

        this.tryAndReplaceAllTasks(entity, entity.targetSelector, NonTameRandomTargetGoal.class, goal -> {
            try {
                TargetGoalAccessor targetGoalAccessor = (TargetGoalAccessor) goal;
                NearestAttackTargetGoalAccessor<?> nearestAttackTargetGoalAccessor = (NearestAttackTargetGoalAccessor<?>) goal;
                return new NearestAttackableTargetFastGoal(targetGoalAccessor.getMob(), nearestAttackTargetGoalAccessor.getTarget(), targetGoalAccessor.isMustSee(), targetGoalAccessor.isMustReach(), nearestAttackTargetGoalAccessor.getTargetingConditions()) {
                    private TamableAnimal tamableMob;

                    public Goal build() throws IllegalArgumentException {
                        this.tamableMob = (TamableAnimal) targetGoalAccessor.getMob();
                        return this;
                    }

                    @Override
                    public boolean canUse() {
                        return !this.tamableMob.isInLove() && super.canUse();
                    }

                    @Override
                    public boolean canContinueToUse() {
                        return this.targetConditions != null ? this.targetConditions.test(this.mob, this.target) : super.canContinueToUse();
                    }
                }.build();
            } catch (Exception e) {
                return null;
            }
        });
    }

}
