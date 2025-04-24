package com.carehires.steps.providers;

import com.carehires.actions.providers.ProviderBillingInformationActions;
import com.carehires.utils.BasePage;
import io.cucumber.java.en.And;

public class ProviderBillingInformationSteps {

    ProviderBillingInformationActions billingInformationActions;

    {
        try {
            billingInformationActions = new ProviderBillingInformationActions();
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

    @And("User enters General Billing Information")
    public void enterGeneralBillingInformation() {
        billingInformationActions.savingGeneralBillingInformation();
    }

    @And("User moves to Billing Information and edit data")
    public void moveToBillingInformationAndEditData() {
        billingInformationActions.updatingGeneralBillingInformation();
    }

    @And("User enters General and Custom Billing Information")
    public void enterGeneralAndCustomBillingInformation() {
        billingInformationActions.savingGeneralAndCustomBillingInformation();
    }

    @And("Provider user moves to Billing Information and edit data")
    public void billingInformationAndEditData() {
        billingInformationActions.updatingBillingInformation();
    }
}
