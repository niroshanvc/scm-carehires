package com.carehires.steps.agency;

import com.carehires.actions.agency.CreateAgencyBasicInformationActions;
import io.cucumber.java.en.When;

public class CreateAgencyBasicInformationSteps {
    CreateAgencyBasicInformationActions agencyBasicInformationActions = new CreateAgencyBasicInformationActions();

    @When("^User enters valid agency - basic information$")
    public void enterBasicInfo() {
        agencyBasicInformationActions.enterBasicInfo();
    }
}
