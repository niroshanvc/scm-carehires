package com.carehires.steps.providers;

import com.carehires.actions.providers.ProviderUserManagementActions;
import io.cucumber.java.en.And;

public class ProviderUserManagementSteps {

    ProviderUserManagementActions userManagementActions = new ProviderUserManagementActions();

    @And("User enters valid provider - user management data")
    public void addUserManagement() {
        userManagementActions.addUser();
    }

    @And("User moves to Provider - User Management and edit data")
    public void moveToUserManagementAndEditData() {
        userManagementActions.updateUserData();
    }
}
