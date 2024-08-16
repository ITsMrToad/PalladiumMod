package com.mr_toad.palladium.core.mixin;

import com.mr_toad.palladium.core.Palladium;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    @Shadow @Final private boolean fading;
    @Shadow private long fadeInStart;

    protected TitleScreenMixin(Component c) {
        super(c);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/internal/BrandingControl;forEachAboveCopyrightLine(Ljava/util/function/BiConsumer;)V", shift = At.Shift.AFTER))
    public void renderWarnSodiumAnalog(GuiGraphics graphics, int mx, int my, float partialTicks, CallbackInfo ci) {
        if (Palladium.showTitleScreenWarn()) {
            float f = this.fading ? (float) (Util.getMillis() - this.fadeInStart) / 1000.0F : 1.0F;
            float f1 = this.fading ? Mth.clamp(f - 1.0F, 0.0F, 1.0F) : 1.0F;
            int i = Mth.ceil(f1 * 255.0F) << 24;
            if ((i & -67108864) != 0) {
                graphics.drawCenteredString(this.font, Palladium.SODIUM_FORKS_COMPAT.warnMsg(), this.width / 2, 4, 0xFFFFFF | i);
            }
        }
    }
}
