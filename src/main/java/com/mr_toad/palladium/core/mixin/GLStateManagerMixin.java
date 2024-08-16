package com.mr_toad.palladium.core.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mr_toad.palladium.client.shader.ShaderCacheLoader;
import com.mr_toad.palladium.core.Palladium;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GlStateManager.class)
public abstract class GLStateManagerMixin {
    @Inject(method = "_glGetUniformLocation", at = @At("RETURN"), cancellable = true)
    private static void getUniform(int u, CharSequence sequence, CallbackInfoReturnable<Integer> cir) {
        if (Palladium.checkConfigAndGetValue(cfg -> cfg.enableShaderUniformCaching)) {
            cir.setReturnValue(ShaderCacheLoader.uniform(u, sequence));
        }
    }
}
