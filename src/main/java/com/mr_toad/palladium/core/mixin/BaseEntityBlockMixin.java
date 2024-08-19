package com.mr_toad.palladium.core.mixin;

import com.mr_toad.palladium.common.block.entity.BlockEntityRendererFreezerManager;
import com.mr_toad.palladium.core.Palladium;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BaseEntityBlock.class)
public abstract class BaseEntityBlockMixin {

    @Inject(method = "getRenderShape", at = @At("RETURN"), cancellable = true)
    public void getFrozenShape(BlockState state, CallbackInfoReturnable<RenderShape> cir) {
        if (BlockEntityRendererFreezerManager.canReplace(state)) {
            cir.setReturnValue(RenderShape.MODEL);
        }
    }
}
