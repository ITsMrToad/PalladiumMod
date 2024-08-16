package com.mr_toad.palladium.integration.mod;

import com.mr_toad.lib.api.integration.Integrable;

public class CreateIntegration implements Integrable {

    public static final CreateIntegration INTEGRATION = new CreateIntegration();

    @Override
    public String modid() {
        return "create";
    }
}
