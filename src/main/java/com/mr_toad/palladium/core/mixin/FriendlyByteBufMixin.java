package com.mr_toad.palladium.core.mixin;

import com.mr_toad.palladium.core.Palladium;
import net.minecraft.network.FriendlyByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(FriendlyByteBuf.class)
public abstract class FriendlyByteBufMixin {

    @ModifyConstant(method = "readNbt()Lnet/minecraft/nbt/CompoundTag;", constant = @Constant(longValue = 0x200000L))
    private long getMaxNBTCompoundTagPacketSize(long size) {
        return Palladium.configInt(cfg -> cfg.maxNbtPacketSize, size);
    }
}
