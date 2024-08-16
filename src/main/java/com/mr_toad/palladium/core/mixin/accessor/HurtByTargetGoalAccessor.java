package com.mr_toad.palladium.core.mixin.accessor;

import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HurtByTargetGoal.class)
public interface HurtByTargetGoalAccessor {

    @Accessor("toIgnoreDamage")
    Class<?>[] getClasses();

}
