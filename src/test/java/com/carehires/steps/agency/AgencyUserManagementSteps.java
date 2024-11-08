package com.carehires.steps.agency;

import com.carehires.actions.agency.AgencyUserManagementActions;
import io.cucumber.java.en.And;

public class AgencyUserManagementSteps {

    AgencyUserManagementActions userManagement = new AgencyUserManagementActions();

    @And("^User adds User Management data$")
    public void addUserManagement() {
        userManagement.addUser();
    }

    @And("User moves to User Management and edit data")
    public void editUserManagementData() {
        userManagement.editUserManagement();
    }
}
