package com.carehires.steps.worker;

import com.carehires.actions.workers.CreateWorkerEmploymentHistoryActions;
import io.cucumber.java.en.And;

public class CreateWorkerEmploymentHistorySteps {

    CreateWorkerEmploymentHistoryActions employmentHistoryActions = new CreateWorkerEmploymentHistoryActions();

    @And("User enters Employment Information data")
    public void enterEmploymentHistory() {
        employmentHistoryActions.enterDataForEmploymentHistory();
    }
}