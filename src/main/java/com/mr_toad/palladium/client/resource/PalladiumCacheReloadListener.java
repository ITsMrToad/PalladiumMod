package com.mr_toad.palladium.client.resource;

import com.mr_toad.palladium.core.Palladium;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PalladiumCacheReloadListener extends SimplePreparableReloadListener<Void> {

    public static final PalladiumCacheReloadListener LISTENER = new PalladiumCacheReloadListener();

    @Override
    protected Void prepare(ResourceManager manager, ProfilerFiller profiler) {
        Palladium.QUADS.clearCache();
        Palladium.KEY_REGISTRY.clearCache();
        Palladium.KEY_LOCATION.clearCache();
        return null;
    }

    @Override
    protected void apply(Void v, ResourceManager manager, ProfilerFiller profiler) {
        Palladium.LOGGER.info("====De-duplication statistics====");

        Palladium.LOGGER.info("Baked quads cache: {}", Palladium.QUADS);
        Palladium.LOGGER.info("Resource key registry cache: {}", Palladium.KEY_REGISTRY);
        Palladium.LOGGER.info("Resource key location cache: {}", Palladium.KEY_LOCATION);

        Palladium.LOGGER.info("Properties cache: {}", Palladium.PROPERTIES);
        Palladium.LOGGER.info("Namespace cache: {}", Palladium.NAMESPACES);
        Palladium.LOGGER.info("Path cache: {}", Palladium.PATH);
    }
}
