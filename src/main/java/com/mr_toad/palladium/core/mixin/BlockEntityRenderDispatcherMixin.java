package com.mr_toad.palladium.core.mixin;

import com.mr_toad.palladium.common.block.entity.BlockEntityRendererFreezerManager;
import com.mr_toad.palladium.core.Palladium;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityRenderDispatcher.class)
public abstract class BlockEntityRenderDispatcherMixin {

    @Inject(method = "getRenderer", at = @At("HEAD"), cancellable = true)
    private <E extends BlockEntity> void removeIfFrozen(E block, CallbackInfoReturnable<BlockEntityRenderer<E>> cir) {
        if (BlockEntityRendererFreezerManager.canReplace(block.getBlockState())) {
            cir.setReturnValue(null);
        }
    }
}
