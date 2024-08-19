package com.mr_toad.palladium.core;

import com.mr_toad.palladium.common.Deduplicator;
import com.mr_toad.palladium.core.config.PalladiumConfig;
import com.mr_toad.palladium.core.config.ResourceLocationDeduplication;
import com.mr_toad.palladium.integration.SodiumForksCompat;
import it.unimi.dsi.fastutil.Hash;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
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

    public static PalladiumConfig CONFIG = PalladiumConfig.loadOrCreate();

    public Palladium() {     
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static boolean isResourceDeduplication(ResourceLocationDeduplication only) {
        if (CONFIG.resourceLocationDeduplication == ResourceLocationDeduplication.ALL) {
             return true;
         } else if (CONFIG.resourceLocationDeduplication == ResourceLocationDeduplication.NONE) {
            return false;
         } else {
            return CONFIG.resourceLocationDeduplication == only;
         }
    }

    public static ResourceLocation makeRl(String path) {
        return new ResourceLocation(MODID, path);
    }
}
