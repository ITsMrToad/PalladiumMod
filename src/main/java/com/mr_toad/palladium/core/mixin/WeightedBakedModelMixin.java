package com.mr_toad.palladium.core.mixin;

import com.mr_toad.palladium.common.util.ImmutableArrayList;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.WeightedBakedModel;
import net.minecraft.util.random.WeightedEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(WeightedBakedModel.class)
public abstract class WeightedBakedModelMixin {

    @Mutable @Shadow @Final private List<WeightedEntry.Wrapper<BakedModel>> list;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void redirectList(List<WeightedEntry.Wrapper<BakedModel>> list, CallbackInfo ci) {
        this.list = new ImmutableArrayList<>(this.list);
    }


}
