package com.mr_toad.palladium.common.entity.processor;

import com.mr_toad.lib.mtjava.strings.func.StringSupplier;
import com.mr_toad.palladium.common.entity.path.AvoidGoalMapperProvider;
import com.mr_toad.palladium.common.entity.path.TargetGoalMapperProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

@FunctionalInterface
public interface EntityAiMappingProcessor {

    EntityAiMappingProcessor VANILLA = (mob, loc) -> {
        new TargetGoalMapperProvider().runIfCan(mob, loc);
        new AvoidGoalMapperProvider().runIfCan(mob, loc);
    };

    void process(Mob entity, ResourceLocation location);

    default boolean canUse() {
        return true;
    }
}
