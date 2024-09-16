package com.carehires.steps;

import com.carehires.actions.CreateAgencyBusinessLocationsActions;
import io.cucumber.java.en.And;

public class CreateAgencyBusinessLocationSteps {

    CreateAgencyBusinessLocationsActions locationsActions = new CreateAgencyBusinessLocationsActions();

    @And("User adds agency business location")
    public void userAddsAgencyBusinessLocation() {
        locationsActions.enterLocationDetails();
    }
}
