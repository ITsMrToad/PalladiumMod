package com.mr_toad.palladium.core.mixin;

import com.mr_toad.lib.mtjava.collections.ImmutableArrayList;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.core.Direction;
import net.minecraftforge.client.RenderTypeGroup;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;

@Mixin(SimpleBakedModel.class)
public abstract class SimpleBakedModelMixin {

    @Mutable @Shadow @Final protected List<BakedQuad> unculledFaces;
    @Shadow @Final protected Map<Direction, List<BakedQuad>> culledFaces;

    @Inject(method = "<init>(Ljava/util/List;Ljava/util/Map;ZZZLnet/minecraft/client/renderer/texture/TextureAtlasSprite;Lnet/minecraft/client/renderer/block/model/ItemTransforms;Lnet/minecraft/client/renderer/block/model/ItemOverrides;Lnet/minecraftforge/client/RenderTypeGroup;)V", at = @At("RETURN"))
    private void initialization(List<BakedQuad> quads, Map<Direction, List<BakedQuad>> faces, boolean ao, boolean b1, boolean b2, TextureAtlasSprite sprite, ItemTransforms transforms, ItemOverrides overrides, RenderTypeGroup renderTypes, CallbackInfo ci) {
        this.unculledFaces = new ImmutableArrayList<>(this.unculledFaces);
        for (Map.Entry<Direction, List<BakedQuad>> entry : this.culledFaces.entrySet()) {
            entry.setValue(new ImmutableArrayList<>(entry.getValue()));
        }
    }

}
