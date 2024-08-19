package com.mr_toad.palladium.core.mixin;

import com.mr_toad.palladium.client.model.ModelResourceLocationProperties;
import com.mr_toad.palladium.core.Palladium;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import org.jetbrains.annotations.Nullable;
import java.util.Arrays;
import java.util.function.Supplier;

@Mixin(ModelResourceLocation.class)
public abstract class ModelResourceLocationMixin extends ResourceLocation implements ModelResourceLocationProperties {

    @Mutable @Shadow @Final private String variant;
    @Unique private String[] palladium$properties;

    protected ModelResourceLocationMixin(String s, String s2, @Nullable Dummy dummy) {
        super(s, s2, dummy);
    }

    @Inject(method = "<init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lnet/minecraft/resources/ResourceLocation$Dummy;)V", at = @At("RETURN"))
    private void cacheInit(String namespace, String path, String var, ResourceLocation.Dummy dummy, CallbackInfo ci) {
        this.palladium$declareProperties();
        this.variant = String.join(",", this.palladium$properties());
    }

    @Inject(method = "<init>(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", at = @At("RETURN"))
    private void cacheInitVanilla(String namespace, String path, String var, CallbackInfo ci) {
        this.palladium$declareProperties();
        this.variant = String.join(",", this.palladium$properties());
    }

    
    @Inject(method = "getVariant", at = @At("RETURN"), cancellable = true)
    private void getVariantByProperties(CallbackInfoReturnable<String> cir) {
        if (this.palladium$properties().length != 0) {
            cir.setReturnValue(String.join(",", this.palladium$properties()));
        }
    }

    @Inject(method = "hashCode", at = @At("RETURN"), cancellable = true, expect = 3)
    private void hashArray(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(31 * cir.getReturnValue() + Arrays.hashCode(this.palladium$properties()));
    }

    @Override
    public String[] palladium$properties() {
        return this.palladium$properties;
    }

     @Unique private void palladium$declareProperties() {
        this.palladium$properties = Arrays.stream(this.variant.split(",")).map(Palladium.PROPERTIES::deduplicate).toArray(String[]::new);
     }
}
