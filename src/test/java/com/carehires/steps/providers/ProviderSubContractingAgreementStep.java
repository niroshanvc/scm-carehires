package com.carehires.steps.providers;

import com.carehires.actions.providers.ProviderSubContractingAgreementActions;
import io.cucumber.java.en.When;

public class ProviderSubContractingAgreementStep {

    ProviderSubContractingAgreementActions subContract = new ProviderSubContractingAgreementActions();

    @When("User clicks on the Complete Profile button on the Contract Agreement page")
    public void completeProfileButton() {
        subContract.clickOnCompleteProfileButton();
    }
}
