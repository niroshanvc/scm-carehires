package com.carehires.steps.agency;

import com.carehires.actions.agency.CreateAgencyStaffActions;
import io.cucumber.java.en.And;

public class CreateAgencyStaffSteps {

    CreateAgencyStaffActions staffActions = new CreateAgencyStaffActions();

    @And("User adds agency staff data")
    public void enterStaffInfo() {
        staffActions.addStaff();
    }
}
