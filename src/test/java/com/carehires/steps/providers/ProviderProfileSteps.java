package com.carehires.steps.providers;

import com.carehires.actions.providers.ProviderProfileActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class ProviderProfileSteps {

    ProviderProfileActions profileActions = new ProviderProfileActions();

    @And("User clicks on the approve button on provider information page")
    public void clickOnApproveButton() {
        profileActions.updateProfileAsApprove();
    }

    @And("User again navigates to the Provider Profile page")
    public void navigateToProfilePage() {
        profileActions.moveToProfilePage();
    }

    @Then("^User verifies the provider profile status as (.*)$")
    public void verifyProfileStatus(String status) {
        profileActions.verifyProfileStatus(status);
    }
}
