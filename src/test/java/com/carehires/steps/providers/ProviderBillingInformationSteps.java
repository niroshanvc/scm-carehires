package com.carehires.steps.providers;

import com.carehires.actions.providers.ProviderBillingInformationActions;
import io.cucumber.java.en.And;

public class ProviderBillingInformationSteps {

    ProviderBillingInformationActions billingInformationActions = new ProviderBillingInformationActions();

    @And("User enters General Billing Information")
    public void enterGeneralBillingInformation() {
        billingInformationActions.savingGeneralBillingInformation();
    }

    @And("User moves to Billing Information and edit data")
    public void moveToBillingInformationAndEditData() {
        billingInformationActions.updatingGeneralBillingInformation();
    }
}
