package com.carehires.steps.providers;

import com.carehires.actions.providers.CreateProviderCompanyInformationActions;
import io.cucumber.java.en.And;

public class CreateProvidersCompanyInformationSteps {

    CreateProviderCompanyInformationActions companyInformationActions = new CreateProviderCompanyInformationActions();

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
