package com.carehires.steps.providers;

import com.carehires.actions.providers.ProviderCompanyInformationActions;
import com.carehires.utils.BasePage;
import io.cucumber.java.en.And;

public class ProviderCompanyInformationSteps {

    ProviderCompanyInformationActions companyInformationActions;

    {
        try {
            companyInformationActions = new ProviderCompanyInformationActions();
        } catch (BasePage.WebDriverInitializationException e) {
            throw new RuntimeException(e);
        }
    }

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

    @And("User creates a provider")
    public void createProvider() {
        companyInformationActions.completeProviderCreationSteps();
    }

    @And("Provider user updates provider profile data")
    public void updateProviderProfileData() {
        companyInformationActions.updateProviderProfileData();
    }
}
