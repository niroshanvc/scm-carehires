package com.carehires.steps.providers;

import com.carehires.actions.providers.CreateProvidersUserManagementActions;
import io.cucumber.java.en.And;

public class CreateProvidersUserManagementSteps {

    CreateProvidersUserManagementActions userManagementActions = new CreateProvidersUserManagementActions();

    @And("User enters valid provider - user management data")
    public void addUserManagement() {
        userManagementActions.addUser();
    }
}
