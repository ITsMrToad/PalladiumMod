package com.mr_toad.palladium.api;

import net.minecraft.world.entity.Mob;

public interface RadiusGoal {

    boolean getRadiusConfig();

    default double defaultRadius() {
        return 2.0D;
    }

    default double getMaxEntityRadius(Mob mob) {
        if(this.getRadiusConfig()) {
            return this.defaultRadius();
        }
        return mob.level().getMaxEntityRadius();
    }
}
