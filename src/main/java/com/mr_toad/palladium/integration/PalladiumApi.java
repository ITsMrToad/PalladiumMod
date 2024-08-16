package com.mr_toad.palladium.integration;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mr_toad.lib.api.integration.Integrable;
import com.mr_toad.palladium.client.model.ModelResourceLocationProperties;
import com.mr_toad.palladium.common.util.GoodImmutableMap;
import com.mr_toad.palladium.common.util.ImmutableArrayList;
import com.mr_toad.palladium.core.Palladium;
import com.mr_toad.palladium.integration.mod.EmbeddiumIntegration;
import com.mr_toad.palladium.integration.mod.XenonIntegration;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.fml.ModList;
import oshi.util.tuples.Pair;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PalladiumApi {

    public static<K> List<K> safeCreateImmutableArrayList(List<K> src) {
        if (ModList.get().isLoaded(Palladium.MODID)) {
            return new ImmutableArrayList<>(src);
        } else {
            return ImmutableList.copyOf(src);
        }
    }

    public static<K, V> Map<K, V> safeCreateImmutableGoodMap(Map<K, V> of) {
        if (ModList.get().isLoaded(Palladium.MODID)) {
            return new GoodImmutableMap<>(of);
        } else {
            return ImmutableMap.copyOf(of);
        }
    }

    public static String[] getModelRLProperties(ModelResourceLocation modelResourceLocation) {
        ModelResourceLocationProperties properties = (ModelResourceLocationProperties) modelResourceLocation;
        return properties.palladium$properties();
    }

    public static boolean isSodiumForkInstaled() {
        return getInstalledSodiumFork().isPresent();
    }

    public static Optional<Integrable> getInstalledSodiumFork() {
        String s = Palladium.SODIUM_FORKS_COMPAT.getInstalled();
        if (s.equals("embeddium")) {
            return Optional.of(EmbeddiumIntegration.INTEGRATION);
        } else if (s.equals("xenon")) {
            return Optional.of(XenonIntegration.INTEGRATION);
        }
        return Optional.empty();
    }
}
