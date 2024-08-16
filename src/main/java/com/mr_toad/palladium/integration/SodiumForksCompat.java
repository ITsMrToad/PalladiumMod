package com.mr_toad.palladium.integration;

import com.mr_toad.palladium.integration.mod.EmbeddiumIntegration;
import com.mr_toad.palladium.integration.mod.XenonIntegration;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.Objects;

public record SodiumForksCompat(String needed) {

    public String getInstalled() {
        String s = "none";

        if (XenonIntegration.INTEGRATION.isLoaded()) {
            s = XenonIntegration.INTEGRATION.modid();
        }

        if (EmbeddiumIntegration.INTEGRATION.isLoaded()) {
            s = EmbeddiumIntegration.INTEGRATION.modid();
        }

        return s;
    }

    public boolean hasFork() {
        return !Objects.equals(this.getInstalled(), "none");
    }

    public Component warnMsg() {
        return Component.translatable("palladium.sodium_forks_incompatible", this.getInstalled(), this.needed(), this.getInstalled()).withStyle(ChatFormatting.RED);
    }
}
