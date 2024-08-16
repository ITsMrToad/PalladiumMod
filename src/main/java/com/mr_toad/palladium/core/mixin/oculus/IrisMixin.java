package com.mr_toad.palladium.core.mixin.oculus;

import com.mr_toad.palladium.client.shader.ShaderCacheLoader;
import com.mr_toad.palladium.core.Palladium;
import net.irisshaders.iris.Iris;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Iris.class, remap = false)
public abstract class IrisMixin {

    @Inject(method = "reload", at = @At("HEAD"))
    private static void reloadCache(CallbackInfo ci) {
        ShaderCacheLoader.reload("Oculus Shader Reload");
    }

}
