package com.mr_toad.palladium.common.entity.path;

import com.mr_toad.palladium.api.GoalMapperProvider;
import com.mr_toad.palladium.common.entity.ai.goal.AvoidEntityFastGoal;
import com.mr_toad.palladium.core.Palladium;
import com.mr_toad.palladium.core.mixin.accessor.AvoidEntityGoalAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.Llama;

@SuppressWarnings({"rawtypes", "unchecked"})
public class AvoidGoalMapperProvider implements GoalMapperProvider {

    @Override
    public boolean canRun() {
        return Palladium.configBoolean(cfg -> cfg.lightweightWolfAndRabbitFleeAi);
    }

    @Override
    public void run(Mob entity, ResourceLocation location) {
        this.replaceAvoidsAll(entity);
        switch (location.getPath()) {
            case "rabbit":
                this.replaceAvoidsRabbit(entity);
                break;
            case "wolf":
                this.replaceAvoidsWolf(entity);
                break;
            default:
        }
    }

    private void replaceAvoidsAll(Mob mobIn) {
        this.tryAndReplaceAllTasks(mobIn, mobIn.goalSelector, AvoidEntityGoal.class, goal -> {
            try {
                AvoidEntityGoalAccessor accessor = (AvoidEntityGoalAccessor) goal;
                return new AvoidEntityFastGoal(accessor.getMob(), accessor.getAvoidClass(), accessor.getAvoidPredicate(), accessor.getMaxDist(), accessor.getWalkSpeedModifier(), accessor.getSprintSpeedModifier(), accessor.getOnAvoidPredicate());
            } catch (Exception e) {
                return null;
            }
        });
    }

    private void replaceAvoidsRabbit(Mob mobIn) {
        if (mobIn.getType() != EntityType.RABBIT) return;

        try {
            tryAndReplaceAllTasks(mobIn, mobIn.goalSelector, Class.forName("net.minecraft.world.entity.animal.Rabbit.RabbitAvoidEntityGoal"), goal -> {
                try {
                    AvoidEntityGoalAccessor accessor = (AvoidEntityGoalAccessor) goal;
                    return new AvoidEntityFastGoal(accessor.getMob(), accessor.getAvoidClass(), accessor.getAvoidPredicate(), accessor.getMaxDist(), accessor.getWalkSpeedModifier(), accessor.getSprintSpeedModifier(), accessor.getOnAvoidPredicate()) {
                        private Rabbit rabbit;

                        public Goal build() throws IllegalArgumentException {
                            this.rabbit = (Rabbit) accessor.getMob();
                            return this;
                        }

                        @Override
                        public boolean canUse() {
                            return this.rabbit.getVariant().id() != 99 && super.canUse();
                        }
                    }.build();
                } catch (Exception e) {
                    return null;
                }
            });
        } catch (ClassNotFoundException e) {
            Palladium.LOGGER.error("Failed to find class", e);
        }
    }

    private void replaceAvoidsWolf(Mob mobIn) {
        if (mobIn.getType() != EntityType.WOLF) return;
        try {
            this.tryAndReplaceAllTasks(mobIn, mobIn.goalSelector, Class.forName("net.minecraft.world.entity.animal.Wolf.WolfAvoidEntityGoal"), goal -> {
                try {
                    AvoidEntityGoalAccessor accessor = (AvoidEntityGoalAccessor) goal;
                    return new AvoidEntityFastGoal(accessor.getMob(), accessor.getAvoidClass(), accessor.getAvoidPredicate(), accessor.getMaxDist(), accessor.getWalkSpeedModifier(), accessor.getSprintSpeedModifier(), accessor.getOnAvoidPredicate()) {
                        private Wolf wolf;

                        public Goal build() throws IllegalArgumentException {
                            this.wolf = (Wolf) accessor.getMob();
                            return this;
                        }

                        @Override
                        public boolean canUse() {
                            if (super.canUse() && this.toAvoid instanceof Llama) {
                                return !this.wolf.isTame() && this.avoidLlama((Llama) this.toAvoid);
                            } else {
                                return false;
                            }
                        }

                        private boolean avoidLlama(Llama avoiderIn) {
                            return avoiderIn.getStrength() >= this.wolf.getRandom().nextInt(5);
                        }

                        @Override
                        public void start() {
                            this.wolf.setTarget(null);
                            super.start();
                        }

                        @Override
                        public void tick() {
                            this.wolf.setTarget(null);
                            super.tick();
                        }

                    }.build();
                } catch (Exception e) {
                    return null;
                }
            });
        } catch (ClassNotFoundException e) {
            Palladium.LOGGER.error("Failed to find class", e);
        }
    }
}