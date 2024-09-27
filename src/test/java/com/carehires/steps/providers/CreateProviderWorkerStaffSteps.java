package com.carehires.steps.providers;

import com.carehires.actions.providers.CreateWorkerStaffActions;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CreateProviderWorkerStaffSteps {

    CreateWorkerStaffActions workerStaffActions = new CreateWorkerStaffActions();

    @When("User enters valid worker staff data")
    public void enterStaffManagementData() {
        workerStaffActions.enterWorkerStaffData();
    }

    @Then("User verifies calculated Monthly agency spend value")
    public void verifyMonthlyAgencySpendValue() {
        workerStaffActions.verifyMonthlyAgencySpendValue();
    }

    @And("User creates a new worker staff")
    public void createNewWorkerStaff() {
        workerStaffActions.addWorkerStaff();
    }
}
