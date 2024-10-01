package com.carehires.steps.agency;

import com.carehires.actions.agency.CreateAgencyBasicInformationActions;
import io.cucumber.java.en.And;

public class CreateAgencyBasicInformationSteps {
    CreateAgencyBasicInformationActions agencyBasicInformationActions = new CreateAgencyBasicInformationActions();

    @And("^User enters valid agency - basic information$")
    public void enterBasicInfo() {
        agencyBasicInformationActions.enterBasicInfo();
    }
}