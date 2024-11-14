package com.carehires.steps.providers;

import com.carehires.actions.providers.ProviderCompanyInformationActions;
import io.cucumber.java.en.And;

public class ProvidersCompanyInformationSteps {

    ProviderCompanyInformationActions companyInformationActions = new ProviderCompanyInformationActions();

    @And("User enters valid provider - company information")
    public void enterCompanyInformation() {
        companyInformationActions.enterCompanyInformation();
    }

    @And("User creates a provider in draft stage")
    public void createProviderInDraftStage() {
        companyInformationActions.createProviderInDraftStage();
    }

    @And("User moves to the payment profile page and edit data")
    public void moveToThePaymentProfileAndEditData() {
        companyInformationActions.updatePaymentProfile();
    }
}
