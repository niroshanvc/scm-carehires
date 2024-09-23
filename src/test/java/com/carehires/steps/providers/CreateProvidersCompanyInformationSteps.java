package com.carehires.steps.providers;

import com.carehires.actions.providers.CreateProviderCompanyInformationActions;

public class CreateProvidersCompanyInformationSteps {

    CreateProviderCompanyInformationActions companyInformationActions = new CreateProviderCompanyInformationActions();

    public void enterCompanyInformation() {
        companyInformationActions.enterCompanyInformation();
    }
}
