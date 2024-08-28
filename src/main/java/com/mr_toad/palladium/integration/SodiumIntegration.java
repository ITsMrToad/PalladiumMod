package com.mr_toad.palladium.integration;

import com.mr_toad.palladium.integration.mod.EmbeddiumIntegration;
import com.mr_toad.palladium.integration.mod.XenonIntegration;

import java.util.Objects;

public class SodiumIntegration {

    public static final Integration EMBEDDIUM = () -> "embeddium";
    public static final Integration XENON = () -> "xenon";
    
    public String getInstalled() {
        String s = "none";

        if (EMBEDDIUM.isLoaded()) {
            s = EMBEDDIUM.modid();
        }

        if (XENON.isLoaded()) {
            s = XENON.modid();
        }

        return s;
    }

    public boolean hasFork() {
        return !Objects.equals(this.getInstalled(), "none");
    }
}
