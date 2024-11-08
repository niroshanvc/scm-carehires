package com.carehires.steps.agency;

import com.carehires.actions.agency.AgencyBusinessLocationsActions;
import io.cucumber.java.en.And;

public class AgencyBusinessLocationSteps {

    AgencyBusinessLocationsActions locationsActions = new AgencyBusinessLocationsActions();

    @And("User adds agency business location")
    public void addAgencyBusinessLocation() {
        locationsActions.addLocationDetails();
    }

    @And("User moves to Locations and edit data")
    public void editLocationsData() {
        locationsActions.editLocationsData();
    }
}
