package com.carehires.steps.providers;

import com.carehires.actions.providers.ProviderWorkerStaffActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProviderWorkerStaffSteps {

    ProviderWorkerStaffActions providerWorkerStaffActions = new ProviderWorkerStaffActions();

    @When("User enters valid worker staff data")
    public void enterStaffManagementData() {
        providerWorkerStaffActions.addingWorkerStaffData();
    }

    @Then("User verifies calculated Monthly agency spend value")
    public void verifyMonthlyAgencySpendValue() {
        providerWorkerStaffActions.verifyMonthlyAgencySpendValue();
    }

    @And("User creates a new worker staff")
    public void createNewWorkerStaff() {
        providerWorkerStaffActions.clickingOnAddButton();
    }

    @Then("User verifies calculated Monthly spend value displays in the table grid and moves to the next screen")
    public void verifyMonthlySpendInTableGrid() {
        providerWorkerStaffActions.verifyMonthlySpendDisplayInTableGrid();
        providerWorkerStaffActions.moveToUserManagementPage();
    }

    @And("User moves to Worker Staff and edit data")
    public void moveToWorkerStaffAndEditData() {
        providerWorkerStaffActions.updateStaffData();
    }
}
