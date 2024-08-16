package com.mr_toad.palladium.core.mixin.embeddium;

import com.mr_toad.palladium.core.Palladium;
import com.mr_toad.palladium.core.config.PalladiumConfigBuilder;
import me.jellysquid.mods.sodium.client.gui.options.OptionPage;
import net.minecraft.client.gui.screens.Screen;
import org.embeddedt.embeddium.gui.EmbeddiumVideoOptionsScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = EmbeddiumVideoOptionsScreen.class, remap = false)
public abstract class EmbeddiumVideoOptionsScreenMixin {

    @Shadow @Final private List<OptionPage> pages;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void addPalladiumPage(Screen prev, List<OptionPage> pages, CallbackInfo ci) {
        if (Palladium.CONFIG != null) {
            new PalladiumConfigBuilder(Palladium.CONFIG).build(this.pages);
        }
    }

}
