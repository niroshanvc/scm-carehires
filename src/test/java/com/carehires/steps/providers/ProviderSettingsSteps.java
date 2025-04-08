package com.carehires.steps.providers;

import com.carehires.actions.providers.ProviderSettingsActions;
import io.cucumber.java.en.And;

public class ProviderSettingsSteps {

    ProviderSettingsActions settings = new ProviderSettingsActions();

    @And("^User enables visibility and disable mandatory of (.*)$")
    public void enableVisibility(String siteName) {
        settings.enableVisibilityAndDisableMandatory(siteName);
    }
}
