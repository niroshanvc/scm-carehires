package com.carehires.steps.providers;

import com.carehires.actions.providers.ProviderSettingsActions;
import io.cucumber.java.en.And;

public class ProviderSettingsSteps {

    ProviderSettingsActions settings = new ProviderSettingsActions();

    @And("^User enables visibility and disable mandatory of (.*)$")
    public void enableVisibility(String siteName) {
        settings.enableVisibilityAndDisableMandatory(siteName);
    }

    @And("^User enables both visibility and mandatory of (.*)$")
    public void enableVisibilityAndMandatory(String  siteNName) {
        settings.enableVisibilityAndMandatory(siteNName);
    }
}
