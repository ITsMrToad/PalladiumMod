package com.mr_toad.palladium.core.mixin;

import com.mr_toad.palladium.core.Palladium;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BakedQuad.class)
public abstract class BakedQuadMixin {

    @Mutable @Shadow @Final protected int[] vertices;

    @Inject(method = "<init>([IILnet/minecraft/core/Direction;Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;ZZ)V", at = @At("RETURN"))
    private void cachedInit(int[] vertices, int color, Direction direction, TextureAtlasSprite sprite, boolean shade, boolean hasAmbientOcclusion, CallbackInfo ci) {
        this.vertices = Palladium.QUADS.deduplicate(this.vertices);
    }
}



