package com.mr_toad.palladium.integration;

import com.mr_toad.palladium.integration.mod.EmbeddiumIntegration;
import com.mr_toad.palladium.integration.mod.XenonIntegration;

import java.util.Objects;

public class SodiumForksCompat {

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

}
