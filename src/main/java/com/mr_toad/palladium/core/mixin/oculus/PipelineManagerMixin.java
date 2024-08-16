package com.mr_toad.palladium.core.mixin.oculus;

import com.mr_toad.palladium.client.shader.ShaderCacheLoader;
import net.irisshaders.iris.pipeline.PipelineManager;
import net.irisshaders.iris.pipeline.WorldRenderingPipeline;
import net.irisshaders.iris.shaderpack.materialmap.NamespacedId;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PipelineManager.class, remap = false)
public abstract class PipelineManagerMixin {
    @Inject(method = "preparePipeline", at = @At(value = "INVOKE", target = "Lnet/irisshaders/iris/IrisLogging;info(Ljava/lang/String;[Ljava/lang/Object;)V"))
    private void reloadCacheOnCreatedPipeline(NamespacedId currentDimension, CallbackInfoReturnable<WorldRenderingPipeline> cir) {
        ShaderCacheLoader.reload("new Oculus pipeline");
    }
}
