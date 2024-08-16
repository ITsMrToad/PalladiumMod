package com.mr_toad.palladium.integration.mod;

import com.mr_toad.lib.api.integration.Integrable;

public class XenonIntegration implements Integrable {

    public static final XenonIntegration INTEGRATION = new XenonIntegration();

    @Override
    public String modid() {
        return "xenon";
    }
}
