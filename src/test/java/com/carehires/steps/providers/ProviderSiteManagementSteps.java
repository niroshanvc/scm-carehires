package com.carehires.steps.providers;

import com.carehires.actions.providers.ProviderSiteManagementActions;
import io.cucumber.java.en.And;

public class ProviderSiteManagementSteps {

    ProviderSiteManagementActions siteManagementActions = new ProviderSiteManagementActions();

    @And("User enters valid provider - site management data")
    public void enterSiteManagementData() {
        siteManagementActions.addSiteManagementData();
    }

    @And("User moves to Site and edit data")
    public void moveToSiteAndEditData() {
        siteManagementActions.updateSiteInfo();
    }
}
