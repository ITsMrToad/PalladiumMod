package com.mr_toad.palladium.common.entity.processor;

import com.google.common.collect.Maps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.ApiStatus;
import java.util.Map;

@ApiStatus.Internal
public class EntityAiMappingProcessors {

    public static final Map<String, EntityAiMappingProcessor> PROCESSORS = Maps.newConcurrentMap();

    public static void init() {
        PROCESSORS.put("minecraft", EntityAiMappingProcessor.VANILLA);
    }

    public static void process(Level level, Entity entity) {
        if (!level.isClientSide()) {
            if (entity instanceof Mob mob) {
                ResourceLocation id = EntityType.getKey(entity.getType());
                String namespace = id.getNamespace();
                EntityAiMappingProcessor modProcess = PROCESSORS.get(namespace);
                if (modProcess != null) {
                    if (modProcess.canUse()) {
                        modProcess.process(mob, id);
                    }
                }
            }
        }
    }
}
