package com.mr_toad.palladium.core.mixin;

import com.mr_toad.palladium.core.Palladium;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ResourceKey.class)
public abstract class ResourceKeyMixin {

    @Mutable @Shadow @Final private ResourceLocation registryName;
    @Mutable @Shadow @Final private ResourceLocation location;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void cacheInit(ResourceLocation registry, ResourceLocation location, CallbackInfo ci) {
        if (Palladium.CONFIG.enableResourceKeyDedup) {
            this.registryName = Palladium.KEY_REGISTRY.deduplicate(this.registryName);
            this.location = Palladium.KEY_LOCATION.deduplicate(this.location);
        }
    }

    @Override
    public int hashCode() {
        return this.registryName.hashCode() + this.location.hashCode();
    }
}
