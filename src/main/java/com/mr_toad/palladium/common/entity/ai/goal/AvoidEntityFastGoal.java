package com.mr_toad.palladium.common.entity.ai.goal;

import com.mr_toad.palladium.api.RadiusGoal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.function.Predicate;

public class AvoidEntityFastGoal<T extends LivingEntity> extends Goal implements RadiusGoal {

    protected final PathfinderMob mob;
    private final double walkSpeedModifier;
    private final double sprintSpeedModifier;
    protected T toAvoid;
    protected final float maxDist;
    protected Path path;
    protected final PathNavigation pathNav;
    protected final Class<T> avoidClass;
    protected final Predicate<LivingEntity> avoidPredicate;
    protected final Predicate<LivingEntity> predicateOnAvoidEntity;
    private final TargetingConditions avoidEntityTargeting;

    public AvoidEntityFastGoal(PathfinderMob mobIn, Class<T> avoidClassIn, Predicate<LivingEntity> avoidPredicateIn, float maxDistIn, double walkSpeedModifierIn, double sprintSpeedModifierIn, Predicate<LivingEntity> predicateOnAvoidEntityIn) {
        this.mob = mobIn;
        this.avoidClass = avoidClassIn;
        this.avoidPredicate = avoidPredicateIn;
        this.maxDist = maxDistIn;
        this.walkSpeedModifier = walkSpeedModifierIn;
        this.sprintSpeedModifier = sprintSpeedModifierIn;
        this.predicateOnAvoidEntity = predicateOnAvoidEntityIn;
        this.pathNav = mobIn.getNavigation();
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        this.avoidEntityTargeting = TargetingConditions.forCombat().range(maxDistIn).selector(predicateOnAvoidEntityIn.and(avoidPredicateIn));
    }

    @Override
    public String toString() {
        return "AvoidEntityReducedGoal";
    }


    @SuppressWarnings("unchecked")
    @Override
    public boolean canUse() {
        this.toAvoid = (T) this.mob.level().getNearestEntity(LivingEntity.class, this.avoidEntityTargeting, this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ(), this.mob.getBoundingBox().inflate(this.maxDist, 3.0D, this.maxDist));
        if (this.toAvoid == null) {
            return false;
        } else {
            Vec3 vector3d = DefaultRandomPos.getPosAway(this.mob, 16, 7, this.toAvoid.position());
            if (vector3d == null) {
                return false;
            } else if (this.toAvoid.distanceToSqr(vector3d.x, vector3d.y, vector3d.z) < this.toAvoid.distanceToSqr(this.mob)) {
                return false;
            } else {
                this.path = this.pathNav.createPath(vector3d.x, vector3d.y, vector3d.z, 0);
                return this.path != null;
            }
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.pathNav.isDone();
    }

    @Override
    public void start() {
        this.pathNav.moveTo(this.path, this.walkSpeedModifier);
    }

    @Override
    public void stop() {
        this.toAvoid = null;
    }

    @Override
    public void tick() {
        if (this.mob.distanceToSqr(this.toAvoid) < 49.0D) {
            this.mob.getNavigation().setSpeedModifier(this.sprintSpeedModifier);
        } else {
            this.mob.getNavigation().setSpeedModifier(this.walkSpeedModifier);
        }
    }

    @Override
    public boolean getRadiusConfig() {
        return true;
    }
}
