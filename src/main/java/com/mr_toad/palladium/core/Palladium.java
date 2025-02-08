package com.mr_toad.palladium.core;

import com.mr_toad.lib.api.config.ToadConfigs;
import com.mr_toad.palladium.common.Deduplicator;
import com.mr_toad.palladium.core.config.PalladiumConfig;
import com.mr_toad.palladium.core.config.ResourceLocationDeduplication;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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

    public static final Deduplicator<String> NAMESPACES = new Deduplicator<>(30, 20, TimeUnit.MINUTES);
    public static final Deduplicator<String> PATH = new Deduplicator<>(100, 10, TimeUnit.MINUTES);
    public static final Deduplicator<String> PROPERTIES = new Deduplicator<>(50, 5, TimeUnit.MINUTES);

    public static final Deduplicator<BakedQuad> QUADS = new Deduplicator<>(120, 2, TimeUnit.MINUTES);

    public static final PalladiumConfig CONFIG = new PalladiumConfig();

    public Palladium() {     
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::onCommon);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> bus.addListener(this::clientSetupEvent));
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void onCommon(FMLCommonSetupEvent event) {
        event.enqueueWork(EntityAiMappingProcessors::init);
    }

    private void clientSetupEvent(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ToadConfigs.create(MODID, CONFIG));
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
