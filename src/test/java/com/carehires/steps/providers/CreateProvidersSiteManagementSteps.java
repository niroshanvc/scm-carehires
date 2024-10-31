package com.carehires.steps.providers;

import com.carehires.actions.providers.CreateProvidersSiteManagementActions;
import io.cucumber.java.en.And;

public class CreateProvidersSiteManagementSteps {

    CreateProvidersSiteManagementActions siteManagementActions = new CreateProvidersSiteManagementActions();

    @And("User enters valid provider - site management data")
    public void enterSiteManagementData() {
        siteManagementActions.enterSiteManagementData();
    }
}
