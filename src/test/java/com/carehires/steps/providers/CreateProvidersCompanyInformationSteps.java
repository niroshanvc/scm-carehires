package com.carehires.steps.providers;

import com.carehires.actions.providers.CreateProviderCompanyInformationActions;
import io.cucumber.java.en.And;

public class CreateProvidersCompanyInformationSteps {

    CreateProviderCompanyInformationActions companyInformationActions = new CreateProviderCompanyInformationActions();

    @And("User enters valid provider - company information")
    public void enterCompanyInformation() {
        companyInformationActions.enterCompanyInformation();
    }
}
