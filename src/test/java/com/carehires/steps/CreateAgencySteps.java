package com.carehires.steps;

import com.carehires.actions.CreateAgencyBasicActions;
import io.cucumber.java.en.When;

public class CreateAgencySteps {
    CreateAgencyBasicActions createAgencyBasicActions = new CreateAgencyBasicActions();

    @When("^User enters valid agency - basic information$")
    public void enterBasicInfo() {
        createAgencyBasicActions.enterBasicInfo();
    }
}
