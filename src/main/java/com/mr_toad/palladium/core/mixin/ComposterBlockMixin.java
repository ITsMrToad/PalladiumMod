package com.mr_toad.palladium.core.mixin;


import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public abstract class ComposterBlockMixin {

    @Mixin(targets = "net.minecraft.world.level.block.ComposterBlock.EmptyContainer")
    public static class EmptyContainerMixin {
        @Inject(method = "getSlotsForFace", at = @At("RETURN"), cancellable = true)
        public void getSlotsForFace(Direction direction, CallbackInfoReturnable<int[]> cir) {
            cir.setReturnValue(new int[0]);
        }
    }

    @Mixin(targets = "net.minecraft.world.level.block.ComposterBlock.InputContainer")
    public static class InputContainerMixin {
        @Inject(method = "getSlotsForFace", at = @At("RETURN"), cancellable = true)
        public void getSlotsForFace(Direction direction, CallbackInfoReturnable<int[]> cir) {
            if(direction == Direction.UP) {
                cir.setReturnValue(new int[]{0});
            } else {
                cir.setReturnValue(new int[0]);
            }
        }
    }

    @Mixin(targets = "net.minecraft.world.level.block.ComposterBlock.OutputContainer")
    public static class OutputContainerMixin {
        @Inject(method = "getSlotsForFace", at = @At("RETURN"), cancellable = true)
        public void getSlotsForFace(Direction direction, CallbackInfoReturnable<int[]> cir) {
            if(direction == Direction.DOWN) {
                cir.setReturnValue(new int[]{0});
            } else {
                cir.setReturnValue(new int[0]);
            }
        }
    }

}
