package com.carehires.steps.worker;

import com.carehires.actions.workers.WorkerEmploymentHistoryActions;
import io.cucumber.java.en.And;

public class WorkerEmploymentHistorySteps {

    WorkerEmploymentHistoryActions employmentHistoryActions = new WorkerEmploymentHistoryActions();

    @And("User enters Employment Information data")
    public void enterEmploymentHistory() {
        employmentHistoryActions.enterDataForEmploymentHistory();
    }

    @And("User moves to Employment and edit data")
    public void editEmploymentHistory() {
        employmentHistoryActions.updateEmploymentInfo();
    }

    @And("User collects workerId from Worker - Employment History page")
    public void getWorkerId() {
        employmentHistoryActions.collectWorkerId();
    }
}