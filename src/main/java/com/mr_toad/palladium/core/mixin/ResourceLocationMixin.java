package com.mr_toad.palladium.core.mixin;

import com.mr_toad.palladium.core.Palladium;
import com.mr_toad.palladium.core.config.ResourceLocationDeduplication;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ResourceLocation.class, priority = 500)
public abstract class ResourceLocationMixin implements Comparable<ResourceLocation> {

    @Mutable @Shadow @Final private String namespace;
    @Mutable @Shadow @Final private String path;

    @Inject(method = "<init>([Ljava/lang/String;)V", at = @At("RETURN"))
    private void reinit(String[] id, CallbackInfo ci) {
        if (Palladium.isResourceDeduplication(ResourceLocationDeduplication.ONLY_RESOURCE_LOCATION)) {
            this.namespace = Palladium.NAMESPACES.deduplicate(this.namespace);
            this.path = Palladium.PATH.deduplicate(this.path);
        }
    }
}
