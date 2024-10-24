package com.carehires.steps.worker;

import com.carehires.actions.providers.ProviderProfileActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class ViewWorkerProfileSteps {

    ProviderProfileActions profileActions = new ProviderProfileActions();

    @And("User clicks on the approve button on worker information page")
    public void clickOnApproveButton() {
        profileActions.updateProfileAsApprove();
    }

    @Then("^User verifies the worker profile status as (.*)$")
    public void verifyProfileStatus(String status) {
        profileActions.verifyProfileStatus(status);
    }
}
