package com.carehires.steps.agency;

import com.carehires.actions.agency.CreateAgencyBasicInformationActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class CreateAgencyBasicInformationSteps {
    CreateAgencyBasicInformationActions agencyBasicInformationActions = new CreateAgencyBasicInformationActions();

    @And("^User enters valid agency - basic information$")
    public void enterBasicInfo() {
        agencyBasicInformationActions.enterBasicInfo();
    }

    @Then("User verifies the agent profile status")
    public void verifyProfileStatus() {
        agencyBasicInformationActions.verifyProfileStatus();
    }
}