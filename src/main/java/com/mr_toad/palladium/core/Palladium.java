package com.mr_toad.palladium.core;

import com.mr_toad.lib.mtjava.strings.func.StringSupplier;
import com.mr_toad.palladium.common.Deduplicator;
import com.mr_toad.palladium.common.util.ToBooleanFunction;
import com.mr_toad.palladium.core.config.PalladiumConfig;
import com.mr_toad.palladium.core.config.ResourceLocationDeduplication;
import com.mr_toad.palladium.integration.SodiumForksCompat;
import it.unimi.dsi.fastutil.Hash;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;

@Mod(value = Palladium.MODID)
public class Palladium {

    public static final String MODID = "palladium";
    public static final StringSupplier MOD_VERSION = () -> Objects.requireNonNullElse(ModList.get().getModFileById(MODID).versionString(), "unknown");
    public static final Logger LOGGER = LoggerFactory.getLogger("Palladium");

    public static final Deduplicator<String> NAMESPACES = new Deduplicator<>();
    public static final Deduplicator<String> PATH = new Deduplicator<>();
    public static final Deduplicator<String> PROPERTIES = new Deduplicator<>();

    public static final Deduplicator<int[]> QUADS = new Deduplicator<>(new Hash.Strategy<>() {
        @Override
        public int hashCode(int[] ints) {
            return Arrays.hashCode(ints);
        }

        @Override
        public boolean equals(int[] a, int[] b) {
            return Arrays.equals(a, b);
        }
    });

    public static final Deduplicator<ResourceLocation> KEY_REGISTRY = new Deduplicator<>();
    public static final Deduplicator<ResourceLocation> KEY_LOCATION = new Deduplicator<>();

    public static final SodiumForksCompat SODIUM_FORKS_COMPAT = new SodiumForksCompat(); 

    @Nullable public static PalladiumConfig CONFIG;

    public Palladium() {
        final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        if (SODIUM_FORKS_COMPAT.hasFork()) {
            if (CONFIG == null) {
                CONFIG = PalladiumConfig.loadOrCreate();
            }
        } else {
            if (CONFIG != null) {
                CONFIG = null;
            }
        }

        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("Start loading Palladium mod {}", MOD_VERSION);
    }


    public static boolean isResourceDeduplication(ResourceLocationDeduplication only) {
        return checkConfigAndGetValue(cfg -> {
            if (cfg.resourceLocationDeduplication == ResourceLocationDeduplication.ALL) {
                return true;
            } else if (cfg.resourceLocationDeduplication == ResourceLocationDeduplication.NONE) {
                return false;
            } else {
                return cfg.resourceLocationDeduplication == only;
            }
        });
    }

    public static boolean checkConfigAndGetValue(ToBooleanFunction<PalladiumConfig> getter) {
        if (CONFIG != null) {
            return getter.applyAsBoolean(CONFIG);
        } else {
            return true;
        }
    }

    public static ResourceLocation makeRl(String path) {
        return new ResourceLocation(MODID, path);
    }
}
