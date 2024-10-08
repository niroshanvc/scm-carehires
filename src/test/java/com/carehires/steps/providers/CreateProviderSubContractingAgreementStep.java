package com.carehires.steps.providers;

import com.carehires.actions.providers.CreateProviderSubContractingAgreementActions;
import io.cucumber.java.en.When;

public class CreateProviderSubContractingAgreementStep {

    CreateProviderSubContractingAgreementActions subContract = new CreateProviderSubContractingAgreementActions();

    @When("User clicks on the Complete Profile button on the Contract Agreement page")
    public void completeProfileButton() {
        subContract.clickOnCompleteProfileButton();
    }
}
