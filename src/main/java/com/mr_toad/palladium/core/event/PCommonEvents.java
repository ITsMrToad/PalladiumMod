package com.mr_toad.palladium.core.event;

import com.mr_toad.palladium.client.resource.PalladiumCacheReloadListener;
import com.mr_toad.palladium.client.shader.ShaderCacheLoader;
import com.mr_toad.palladium.core.Palladium;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Palladium.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PCommonEvents {

    @SubscribeEvent
    public static void registerReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener(PalladiumCacheReloadListener.LISTENER);
    }

    @Mod.EventBusSubscriber(modid = Palladium.MODID)
    public static class NoBus {

        @SubscribeEvent
        public static void onLevelLoad(LevelEvent.Save event) {
            BlockEntityRendererFreezerManager.clear();
        }

        @SubscribeEvent
        public static void onLevelLoad(LevelEvent.Load event) {
            BlockEntityRendererFreezerManager.LOAD = Palladium.checkConfigAndGetValueInt(cfg -> cfg.blockEntityReplaceRange) == 0;
            if (Palladium.checkConfigAndGetValue(cfg -> cfg.enableBlockEntityReplace) && BlockEntityRendererFreezerManager.LOAD) {
                BlockEntityRendererFreezerManager.load((Level) event.getLevel(), ((LevelRendererAccessor) Minecraft.getInstance().levelRenderer).getGlobalBlockEntities());
            }
        }

        @SubscribeEvent
        public static void onTickLevel(TickEvent.LevelTickEvent event) {
            if (Palladium.checkConfigAndGetValue(cfg -> cfg.enableBlockEntityReplace) && !BlockEntityRendererFreezerManager.LOAD) {
                BlockEntityRendererFreezerManager.tick(event.level, ((LevelRendererAccessor) Minecraft.getInstance().levelRenderer).getGlobalBlockEntities());
            }
        }
    }
}
