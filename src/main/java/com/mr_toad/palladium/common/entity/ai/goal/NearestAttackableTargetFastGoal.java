package com.mr_toad.palladium.common.entity.ai.goal;

import com.mr_toad.palladium.api.RadiusGoal;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.EnumSet;

public class NearestAttackableTargetFastGoal<T extends LivingEntity> extends TargetGoal implements RadiusGoal {

    protected final Class<T> targetType;
    protected LivingEntity target;
    protected TargetingConditions targetConditions;

    public NearestAttackableTargetFastGoal(Mob mobIn, Class<T> targetTypeIn, boolean mustSeeIn, boolean mustReachIn, TargetingConditions targetConditionsIn) {
        super(mobIn, mustSeeIn, mustReachIn);
        this.targetType = targetTypeIn;
        this.setFlags(EnumSet.of(Flag.TARGET));
        this.targetConditions = targetConditionsIn;
    }

    @Override
    public boolean canUse() {
        this.findTarget();
        return this.target != null;
    }

    @Override
    public void start() {
        this.mob.setTarget(this.target);
        super.start();
    }

    @Override
    public boolean getRadiusConfig() {
        return true;
    }

    protected AABB getTargetSearchArea(double inflateXZIn) {
        return this.mob.getBoundingBox().inflate(inflateXZIn, 4.0D, inflateXZIn);
    }

    protected void findTarget() {
        if (this.targetType != Player.class && this.targetType != ServerPlayer.class) {
            this.target = this.mob.level().getNearestEntity(LivingEntity.class, this.targetConditions, this.mob,  this.mob.getX(), this.mob.getEyeY(), this.mob.getZ(), this.getTargetSearchArea(this.getFollowDistance()));
        } else {
            this.target = this.mob.level().getNearestPlayer(this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
        }
    }

    public void setTarget(@Nullable LivingEntity targetIn) {
        this.target = targetIn;
    }

    @Override
    public @NotNull String toString() {
        return "F-NAT-Goal";
    }
}

