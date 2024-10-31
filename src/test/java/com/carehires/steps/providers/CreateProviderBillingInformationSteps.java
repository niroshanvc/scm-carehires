package com.carehires.steps.providers;

import com.carehires.actions.providers.CreateProviderBillingInformationActions;
import io.cucumber.java.en.And;

public class CreateProviderBillingInformationSteps {

    CreateProviderBillingInformationActions billingInformationActions = new CreateProviderBillingInformationActions();

    @And("User enters General Billing Information")
    public void enterGeneralBillingInformation() {
        billingInformationActions.fillGeneralBillingInformation();
    }
}
