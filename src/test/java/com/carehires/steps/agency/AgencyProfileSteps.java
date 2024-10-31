package com.carehires.steps.agency;

import com.carehires.actions.agency.AgencyProfileActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class AgencyProfileSteps {

    AgencyProfileActions agencyProfileActions = new AgencyProfileActions();

    @And("User completes the profile approve status")
    public void updateProfile() {
        agencyProfileActions.updateProfileAsProfileComplete();
    }

    @And("User again navigates to the Agency Profile page")
    public void navigateToProfilePage() {
        agencyProfileActions.moveToProfilePage();
    }

    @Then("^User verifies the agency profile status as (.*)$")
    public void verifyProfileStatus(String status) {
        agencyProfileActions.verifyProfileStatus(status);
    }

    @And("User approves the profile approve status")
    public void updateProfileAsApprove() {
        agencyProfileActions.updateProfileAsApprove();
    }
}
