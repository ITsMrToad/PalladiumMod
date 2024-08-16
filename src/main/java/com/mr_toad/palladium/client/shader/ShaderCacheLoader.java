package com.mr_toad.palladium.client.shader;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mr_toad.palladium.core.Palladium;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.util.Map;

public class ShaderCacheLoader {

    public static final Int2ObjectMap<ShaderProgramCache> SHADER_CACHE = new Int2ObjectOpenHashMap<>();

    public static void reload(String log) {
        RenderSystem.assertOnRenderThread();
        if (!Palladium.checkConfigAndGetValue(cfg -> cfg.enableShaderUniformCaching)) {
            if (!SHADER_CACHE.isEmpty()) {
                SHADER_CACHE.clear();
            }
            return;
        }

        Palladium.LOGGER.info("Shader cache reload({})", log);
        SHADER_CACHE.clear();
    }

    public static int uniform(int i, CharSequence charSequence) {
        RenderSystem.assertOnRenderThread();
        return SHADER_CACHE.computeIfAbsent(i, v -> new ShaderProgramCache(i)).uniform((String) charSequence);
    }

}
