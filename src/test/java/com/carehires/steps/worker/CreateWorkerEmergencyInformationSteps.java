package com.carehires.steps.worker;

import com.carehires.actions.workers.WorkerEmergencyInformationActions;
import io.cucumber.java.en.And;

public class CreateWorkerEmergencyInformationSteps {

    WorkerEmergencyInformationActions emergencyInformationActions = new WorkerEmergencyInformationActions();

    @And("User enters Emergency Information data")
    public void enterEmergencyInformation() {
        emergencyInformationActions.enterDataForEmergencyInformation();
    }
}