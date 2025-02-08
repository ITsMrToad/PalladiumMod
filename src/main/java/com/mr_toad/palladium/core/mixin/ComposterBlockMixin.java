package com.mr_toad.palladium.core.mixin;


import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public abstract class ComposterBlockMixin {

    private static final Supplier<int[]> ZERO = () -> new int[0];
    private static final Supplier<int[]> ONE = () -> new int[]{0};
    
    @Mixin(targets = "net.minecraft.world.level.block.ComposterBlock.EmptyContainer")
    public static class EmptyContainerMixin {
        @Inject(method = "getSlotsForFace", at = @At("RETURN"), cancellable = true)
        public void getSlotsForFace(Direction direction, CallbackInfoReturnable<int[]> cir) {
            if (Palladium.CONFIG.enableComposterFix.get()) {
                cir.setReturnValue(ZERO.get());
            }
        }
    }

    @Mixin(targets = "net.minecraft.world.level.block.ComposterBlock.InputContainer")
    public static class InputContainerMixin {
        @Inject(method = "getSlotsForFace", at = @At("RETURN"), cancellable = true)
        public void getSlotsForFace(Direction direction, CallbackInfoReturnable<int[]> cir) {
            if (Palladium.CONFIG.enableComposterFix.get()) {
                if (direction == Direction.UP) {
                    cir.setReturnValue(ONE.get());
                } else {
                    cir.setReturnValue(ZERO.get());
                }
            }
        }
    }

    @Mixin(targets = "net.minecraft.world.level.block.ComposterBlock.OutputContainer")
    public static class OutputContainerMixin {
        @Inject(method = "getSlotsForFace", at = @At("RETURN"), cancellable = true)
        public void getSlotsForFace(Direction direction, CallbackInfoReturnable<int[]> cir) {
            if (Palladium.CONFIG.enableComposterFix.get()) {
                if (direction == Direction.DOWN) {
                    cir.setReturnValue(ONE.get());
                } else {
                    cir.setReturnValue(ZERO.get());
                }
            }
        }
    }

}
