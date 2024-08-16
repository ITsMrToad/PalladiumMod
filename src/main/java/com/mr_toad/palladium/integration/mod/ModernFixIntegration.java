package com.mr_toad.palladium.integration.mod;

import com.mr_toad.lib.api.integration.Integrable;

public class ModernFixIntegration implements Integrable {

    public static final ModernFixIntegration INTEGRATION = new ModernFixIntegration();

    @Override
    public String modid() {
        return "modern_fix";
    }
}
