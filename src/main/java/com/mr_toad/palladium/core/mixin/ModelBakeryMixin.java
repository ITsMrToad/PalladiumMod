package com.mr_toad.palladium.core.mixin;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {

    @Mutable @Shadow @Final private Map<ResourceLocation, UnbakedModel> unbakedCache;
    @Mutable @Shadow @Final private Map<ResourceLocation, BlockModel> modelResources;
    @Mutable @Shadow @Final private Map<ResourceLocation, BakedModel> bakedTopLevelModels;
    @Mutable @Shadow @Final private Set<ResourceLocation> loadingStack;
    @Mutable @Shadow @Final private Map<ResourceLocation, UnbakedModel> topLevelModels;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void reinit(BlockColors colors, ProfilerFiller profiler, Map<ResourceLocation, BlockModel> blockModels, Map<ResourceLocation, List<ModelBakery.LoadedJson>> loaded, CallbackInfo ci) {
        this.unbakedCache = new Object2ObjectOpenHashMap<>(this.unbakedCache);
        this.bakedTopLevelModels = new Object2ObjectOpenHashMap<>(this.bakedTopLevelModels);
        this.modelResources = new Object2ObjectOpenHashMap<>(this.modelResources);
        this.topLevelModels = new Object2ObjectOpenHashMap<>(this.topLevelModels);
        this.loadingStack = new ObjectOpenHashSet<>(this.loadingStack);
    }
}
