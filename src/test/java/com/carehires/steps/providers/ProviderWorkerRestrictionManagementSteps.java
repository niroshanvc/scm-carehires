package com.carehires.steps.providers;

import com.carehires.actions.providers.ProviderWorkerRestrictionManagementActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProviderWorkerRestrictionManagementSteps {

    ProviderWorkerRestrictionManagementActions restrictionManagement = new ProviderWorkerRestrictionManagementActions();

    @And("Provider user moves to Restrictions and edit data")
    public void moveToRestrictionsAndEditData() {
        restrictionManagement.addRestriction();
    }

    @When("User accepts a worker pending approval")
    public void acceptWorkerPendingApproval() {
        restrictionManagement.acceptWorker();
    }

    @Then("Provider user verifies added restricted worker displays in the table grid")
    public void verifyRestrictedWorkerDisplay() {
        restrictionManagement.verifyRestrictedWorkerDisplayInTableGrid();
    }

    @And("Provider user removes restricted worker")
    public void removeRestrictedWorker() {
        restrictionManagement.removeRestrictedWorker();
    }
}
