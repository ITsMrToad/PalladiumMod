package com.mr_toad.palladium.integration.mod;

import com.mr_toad.lib.api.integration.Integrable;

public class EmbeddiumIntegration implements Integrable {

    public static final EmbeddiumIntegration INTEGRATION = new EmbeddiumIntegration();

    @Override
    public String modid() {
        return "embeddium";
    }
}
