package com.carehires.steps.agency;

import com.carehires.actions.agency.AgencyStaffActions;
import io.cucumber.java.en.And;

public class AgencyStaffSteps {

    AgencyStaffActions staffActions = new AgencyStaffActions();

    @And("User adds agency staff data")
    public void enterStaffInfo() {
        staffActions.addStaff();
    }

    @And("User moves to Staff and edit data")
    public void editStaffData() {
        staffActions.editStaffData();
    }
}
