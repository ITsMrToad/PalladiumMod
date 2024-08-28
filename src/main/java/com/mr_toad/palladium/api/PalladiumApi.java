package com.mr_toad.palladium.api;

import com.mr_toad.lib.api.integration.Integration;
import com.mr_toad.palladium.client.model.ModelResourceLocationProperties;
import com.mr_toad.palladium.common.entity.processor.EntityAiMappingProcessor;
import com.mr_toad.palladium.common.entity.processor.EntityAiMappingProcessors;
import com.mr_toad.palladium.core.Palladium;
import com.mr_toad.palladium.integration.SodiumIntegration;
import net.minecraft.client.resources.model.ModelResourceLocation;

import java.util.Optional;

public class PalladiumApi {

    public static String[] getModelRLProperties(ModelResourceLocation modelResourceLocation) {
        ModelResourceLocationProperties properties = (ModelResourceLocationProperties) modelResourceLocation;
        return properties.palladium$properties();
    }

    public static boolean isSodiumForkInstaled() {
        return getInstalledSodiumFork().isPresent();
    }

    public static Optional<Integration> getInstalledSodiumFork() {
        String s = Palladium.SODIUM_FORKS_COMPAT.getInstalled();
        if (s.equals("embeddium")) {
            return Optional.of(SodiumIntegration.EMBEDDIUM);
        } else if (s.equals("xenon")) {
            return Optional.of(SodiumIntegration.XENON);
        }
        return Optional.empty();
    }

   
}
