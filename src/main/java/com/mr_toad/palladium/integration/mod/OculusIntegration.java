package com.mr_toad.palladium.integration.mod;

import com.mr_toad.lib.api.integration.Integrable;
import net.irisshaders.iris.api.v0.IrisApi;

public class OculusIntegration implements Integrable {

    public static final OculusIntegration INTEGRATION = new OculusIntegration();

    public boolean isShaderUsed() {
        return this.isLoaded() && IrisApi.getInstance().isShaderPackInUse();
    }

    @Override
    public String modid() {
        return "oculus";
    }
}
