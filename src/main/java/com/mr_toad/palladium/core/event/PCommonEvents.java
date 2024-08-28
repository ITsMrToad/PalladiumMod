package com.mr_toad.palladium.core.event;

import com.mr_toad.palladium.client.resource.PalladiumCacheReloadListener;
import com.mr_toad.palladium.client.shader.ShaderCacheLoader;
import com.mr_toad.palladium.core.Palladium;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Palladium.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PCommonEvents {

    @SubscribeEvent
    public static void registerReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(PalladiumCacheReloadListener.LISTENER);
    }

    @Mod.EventBusSubscriber(modid = Palladium.MODID)
    public static class WithoutBus {
        @SubscribeEvent
        public static void onJoin(EntityJoinLevelEvent event) {
            EntityAiMappingProcessors.process(event.getLevel(), event.getEntity());
        }
    }
}
